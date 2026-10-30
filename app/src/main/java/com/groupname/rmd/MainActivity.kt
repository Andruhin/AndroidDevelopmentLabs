package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


  val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  { result ->
    if (result.resultCode == Activity.RESULT_OK){
      val text = result.data?.getStringExtra("second_key")
      val textView = findViewById<TextView>(R.id.text_view)
      textView.text = text
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val image = findViewById<ImageView>(R.id.image)
    val text = findViewById<TextView>(R.id.text_view)
    val btn = findViewById<Button>(R.id.btn_ok)
    val editView = findViewById<EditText>(R.id.input_field)

    btn.setOnClickListener {
      val text = editView.text.toString()
      launchAddTaskActivityForResult(text)
    }

    Toast.makeText(this, "onCreate MainActivity", Toast.LENGTH_SHORT).show()
  }

  //Launch without waiting for result
  private fun launchAddTaskActivity(text: String){
    val intent = Intent(this, AddTaskActivity::class.java)
    intent.putExtra("key_text", text)
    startActivity(intent)
  }

  //Launch with waiting for result
  private fun launchAddTaskActivityForResult(text: String){
    val intent = Intent(this, AddTaskActivity::class.java)
    intent.putExtra("key_text", text)
   launcher.launch(intent)
  }

  override fun onStart() {
    super.onStart()
    Toast.makeText(this, "onStart MainActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onResume() {
    super.onResume()
    Toast.makeText(this, "onResume MainActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onPause() {
    super.onPause()
    Toast.makeText(this, "onPause MainActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onStop() {
    super.onStop()
    Toast.makeText(this, "onStop MainActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onDestroy() {
    super.onDestroy()
    Toast.makeText(this, "onDestroy MainActivity", Toast.LENGTH_SHORT).show()
  }
}