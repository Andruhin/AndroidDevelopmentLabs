package com.groupname.rmd

import android.content.Context
import com.google.gson.Gson

class Storage(val context: Context) {

  private val key = "key_data"
  private val sharedPreferences =
    context.getSharedPreferences("data", Context.MODE_PRIVATE)

  private val data: Data

  init {
    data = loadData()
  }

  private fun saveData(data: Data){
    val gson = Gson()
    val jsonTask = gson.toJson(data)
    sharedPreferences.edit()
      .putString(key, jsonTask)
      .apply()
  }

  private fun loadData(): Data {
    val gson = Gson()
    val jsonString = sharedPreferences.getString(key, null)
    if (jsonString.isNullOrBlank()){
      return Data()
    } else {
     return gson.fromJson<Data>(jsonString, Data::class.java)
    }
  }

  fun addTask(task: Task){
    data.tasks.add(task)
    saveData(data)
  }

  fun getTasks(): List<Task> {
    return data.tasks
  }

  fun deleteItemById(id: String){
    data.tasks.removeIf { it.id == id }
    saveData(data)
  }

  fun getItemById(id: String): Task? {
    return data.tasks.find { it.id == id }
  }

}