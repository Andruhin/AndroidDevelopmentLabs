package com.groupname.rmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import java.util.Date
import java.util.GregorianCalendar

class MainActivity : AppCompatActivity() {

  val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  { result ->
    if (result.resultCode == Activity.RESULT_OK) {
      val task = result.data?.getParcelableExtra<Task>(AddTaskActivity.KEY_NEW_TASK)

      if (task != null) {
        taskList.add(task)
        storage.saveTaskList(taskList)
        adapter.setData(taskList)
      }
    }
  }

  private val taskList = mutableListOf<Task>()
  private var list: RecyclerView? = null
  private val adapter: ListAdapter = ListAdapter()

  private lateinit var storage: Storage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    list = findViewById<RecyclerView>(R.id.list)
    val layoutManager = LinearLayoutManager(this)
    list?.layoutManager = layoutManager
    list?.adapter = adapter

    storage = Storage(this)
    taskList.addAll(storage.getTaskList())
    adapter.setData(taskList)

    //Example how to store objects in shared preferences
    val task = Task(1,"description", GregorianCalendar.getInstance().time.toString())
    storage.saveTask(task)
    val task2 = storage.getTask()
    Log.d("task", task2.toString())

    // Додаємо розділювач між елементами
    val divider = DividerItemDecoration(this, layoutManager.orientation)
    list?.addItemDecoration(divider)

    val btnAddTask = findViewById<Button>(R.id.btn_add)

    btnAddTask.setOnClickListener {
      launchAddTaskActivityForResult()
    }
  }

  //Launch with waiting for result
  private fun launchAddTaskActivityForResult() {
    val intent = Intent(this, AddTaskActivity::class.java)
    launcher.launch(intent)
  }

}