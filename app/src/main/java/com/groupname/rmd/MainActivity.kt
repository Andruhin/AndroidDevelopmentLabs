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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

  val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  { result ->
    if (result.resultCode == Activity.RESULT_OK) {
      val task = result.data?.getParcelableExtra<Task>(AddTaskActivity.KEY_NEW_TASK)

      if (task != null) {
        storage.saveTask(task)
        adapter.setData(storage.getTasks())
      }
    }
  }

  private var list: RecyclerView? = null
  private val adapter: ListAdapter = ListAdapter()
  private lateinit var storage: Storage

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    storage = Storage(this)

    list = findViewById<RecyclerView>(R.id.list)
    val layoutManager = LinearLayoutManager(this)
    list?.layoutManager = layoutManager
    list?.adapter = adapter
    adapter.setData(storage.getTasks())

    val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
      override fun onMove(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
        val position = viewHolder.bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) return
        val deletedTask = adapter.removeAt(position)
        storage.deleteTask(deletedTask.id)
      }
    }
    ItemTouchHelper(swipeCallback).attachToRecyclerView(list)

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