package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale
import java.util.UUID

class SecondActivity : AppCompatActivity(), OnMapReadyCallback {

  private lateinit var map: GoogleMap
  private lateinit var addressesField: EditText
  private lateinit var taskField: EditText
  private var latLng: LatLng? = null
  private lateinit var storage: Storage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)
    storage = Storage(this)
    val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
    taskField = findViewById<EditText>(R.id.input_field)
    addressesField = findViewById<EditText>(R.id.address)


    val btn = findViewById<Button>(R.id.btn_back)
    btn.setOnClickListener {
      val intent = Intent()
      val description = taskField.text.toString()
      val address = addressesField.text.toString()
      val task = Task(
        id = UUID.randomUUID().toString(),
        description = description,
        address = address,
        latLng = latLng
      )
      intent.putExtra(KEY_TASK, task)
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap

    val id = intent.getStringExtra(KEY_ID_EDIT_TASK)

    val defaultLocation = if (id != null){
      val task = storage.getItemById(id)
      val latLng = task?.latLng ?: LatLng(48.922488, 24.710241)
      map.addMarker(
        MarkerOptions().position(latLng)
      )
      addressesField.setText(task?.address)
      taskField.setText(task?.description)
    latLng
    } else {
      LatLng(48.922488, 24.710241)
    }

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 14f))

    // Add marker on click
    map.setOnMapClickListener { latLng ->
        onMapClick(latLng)
    }
  }

  private fun onMapClick(latLng: LatLng){
    map.clear() // Optional: remove previous markers
    val geocoder = Geocoder(this, Locale.getDefault())
    val latitude = latLng.latitude
    val longitude = latLng.longitude

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      geocoder.getFromLocation(latitude, longitude, 1
      ) { addresses ->
        addresses.firstOrNull()?.let {
          onAddressFound(latLng, it)
        }
      }
    } else {
      val addresses = geocoder.getFromLocation(latitude, longitude, 1)
      addresses?.firstOrNull()?.let {
        onAddressFound(latLng, it)
      }
    }
  }

  private fun onAddressFound(latLng: LatLng, address: Address){
    this.latLng = latLng
    map.clear() // Optional: remove previous markers
    map.addMarker(
      MarkerOptions().position(latLng)
    )
    addressesField.setText(address.getAddressLine(0))
  }

  companion object {
    const val KEY_TASK = "key_task"
    const val KEY_ID_EDIT_TASK = "key_id_edit_task"
  }
}