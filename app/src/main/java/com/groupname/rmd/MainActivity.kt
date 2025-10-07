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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

  val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  { result ->
    if (result.resultCode == Activity.RESULT_OK){
      val task = result.data?.getStringExtra("key_task")
      if (!task.isNullOrBlank()) {
        taskList.add(task)
        adapter.setData(taskList)
      }
    }
  }

  private val taskList = mutableListOf<String>()
  private var list: RecyclerView? = null
  private val adapter: ListAdapter = ListAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    list = findViewById<RecyclerView>(R.id.list)
    val layoutManager = LinearLayoutManager(this)
    list?.layoutManager = layoutManager
    list?.adapter = adapter

    // Додаємо розділювач між елементами
    val divider = DividerItemDecoration(this,  layoutManager.orientation)
    list?.addItemDecoration(divider)

    val btnAddTask = findViewById<Button>(R.id.btn_add)

    btnAddTask.setOnClickListener {
      launchSecondActivityForResult()
    }
  }

  //Launch without waiting for result
  private fun launchSecondActivity(text: String){
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("key_text", text)
    startActivity(intent)
  }

  //Launch with waiting for result
  private fun launchSecondActivityForResult(){
    val intent = Intent(this, SecondActivity::class.java)
   launcher.launch(intent)
  }

}