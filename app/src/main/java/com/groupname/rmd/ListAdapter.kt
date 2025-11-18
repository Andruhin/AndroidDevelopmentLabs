package com.groupname.rmd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/** Клас адаптера, який вміє малювати на списку **/
class ListAdapter(val listener: ListItemListener): RecyclerView.Adapter<ListAdapter.TaskViewHolder>() {

  /** Внутрішня колекція обєктів - список з текстом задач **/
  private val taskList = mutableListOf<Task>()

  /** Метод який записує список задач і запускає їх відмалювання**/
  fun setData(list: List<Task>){
    taskList.clear()
    taskList.addAll(list)
    notifyDataSetChanged()
  }

  /** Створення контейнера елемента **/
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): TaskViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.list_item_task, parent, false)
    return TaskViewHolder(view, listener)
  }

  /** Відмалювання кожного елемента відповідно до позиції**/
  override fun onBindViewHolder(
    holder: TaskViewHolder,
    position: Int
  ) {
    val task = taskList.getOrNull(position)
    holder.render(task)
  }

  /** кількість елементів, що треба відмалювати **/
  override fun getItemCount(): Int {
    return taskList.size
  }

  /** Клас контейнера одного елемента на екрані який вміє відмалювати один елемент **/
  class TaskViewHolder(val view: View, val listener: ListItemListener): RecyclerView.ViewHolder(view){

    private val taskView: TextView? = view.findViewById<TextView>(R.id.task_name)
    private val addressView: TextView? = view.findViewById<TextView>(R.id.task_address)
    private val deleteIcon: ImageView? = view.findViewById<ImageView>(R.id.delete)

    fun render(task: Task?){
      taskView?.text = task?.description
      addressView?.text = task?.address
      deleteIcon?.setOnClickListener {
        listener.onItemDelete(task?.id.orEmpty())
      }
      view.setOnClickListener {
        listener.onItemClick(task?.id.orEmpty())
      }
    }
  }

}