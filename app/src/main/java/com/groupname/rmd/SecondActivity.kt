package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_secnd)

    val editText = findViewById<EditText>(R.id.input_field)

    val btn = findViewById<Button>(R.id.btn_back)
    btn.setOnClickListener {
      val intent = Intent()
      val task = editText.text.toString()
      intent.putExtra("key_task", task)
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }
}