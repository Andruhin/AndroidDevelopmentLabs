package com.groupname.rmd

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val image = findViewById<ImageView>(R.id.image)
    val text = findViewById<TextView>(R.id.text_view)
    val btn = findViewById<Button>(R.id.btn_ok)
    val editView = findViewById<EditText>(R.id.input_field)

    btn.setOnClickListener {
      image.setImageResource(R.drawable.bender_rodriguez)
      val inputText = editView.text.toString()
      text.text = inputText
      editView.setText("")
    }

  }
}