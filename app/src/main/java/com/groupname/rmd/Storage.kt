package com.groupname.rmd

import android.content.Context
import com.google.gson.Gson

class Storage(val context: Context) {

  private val sharedPreferences =
    context.getSharedPreferences("data", Context.MODE_PRIVATE)

  fun saveTaskList(tasks: List<String>){
    sharedPreferences.edit()
      .putStringSet("key_tasks", tasks.toSet())
      .apply()
  }

  fun getTaskList(): List<String> {
    return sharedPreferences.getStringSet("key_tasks", emptySet<String>())
      .orEmpty()
      .toList()
  }

  fun saveTask(task: Task){
    val gson = Gson()
    val jsonTask = gson.toJson(task)
    sharedPreferences.edit()
      .putString("key_task", jsonTask)
      .apply()
  }

  fun getTask(): Task? {
    val gson = Gson()
    val jsonString = sharedPreferences.getString("key_task", null)
    return gson.fromJson<Task>(jsonString, Task::class.java)
  }

}