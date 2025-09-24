package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
    val textView = findViewById<TextView>(R.id.text_view)
    val text = intent.getStringExtra("key_text")
    textView.text = text
    Toast.makeText(this, "onCreate SecondActivity", Toast.LENGTH_SHORT).show()
    val btn = findViewById<Button>(R.id.btn_back)
    btn.setOnClickListener {
      val intent = Intent()
      intent.putExtra("second_key", "Second Activity is closed!")
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onStart() {
    super.onStart()
    Toast.makeText(this, "onStart SecondActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onResume() {
    super.onResume()
    Toast.makeText(this, "onResume SecondActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onPause() {
    super.onPause()
    Toast.makeText(this, "onPause SecondActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onStop() {
    super.onStop()
    Toast.makeText(this, "onStop SecondActivity", Toast.LENGTH_SHORT).show()
  }

  override fun onDestroy() {
    super.onDestroy()
    Toast.makeText(this, "onDestroy SecondActivity", Toast.LENGTH_SHORT).show()
  }
}