package com.groupname.rmd

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class Storage(val context: Context) {

  private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
  private var appData: AppData? = null

  init {
    val addDataStr = sharedPreferences.getString(KEY_APP_DATA, null)
    if (addDataStr == null){
      appData = AppData()
    } else {
      val gson = Gson()
      appData = gson.fromJson<AppData>(addDataStr, AppData::class.java)
    }
  }

  fun saveData(appData: AppData?){
    val data = appData ?: AppData()
    val gson = Gson()
    val appDataStr = gson.toJson(data)
    sharedPreferences.edit().putString(KEY_APP_DATA, appDataStr).apply()
  }

  fun saveTask(task: Task){
    appData = appData?.copy(
      tasks = appData?.tasks?.toMutableList()?.apply { add(task) }.orEmpty()
    )
    saveData(appData)
  }

  fun deleteTask(taskId: String){
    appData = appData?.copy(
      tasks = appData?.tasks?.toMutableList()?.apply { removeAll{ it.id == taskId} }.orEmpty()
    )
    saveData(appData)
  }

  fun getTasks(): List<Task> {
    return appData?.tasks.orEmpty()
  }


  data class AppData(
    val tasks: List<Task> = emptyList(),
    //todo other settings
  )

  companion object {
    private val KEY_APP_DATA = "key appData"
  }
}