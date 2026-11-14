package com.groupname.rmd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/** Клас адаптера, який вміє малювати на списку **/
class ListAdapter: RecyclerView.Adapter<ListAdapter.TaskViewHolder>() {

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
    return TaskViewHolder(view)
  }

  /** Відмалювання кожного елемента відповідно до позиції**/
  override fun onBindViewHolder(
    holder: TaskViewHolder,
    position: Int
  ) {
    val task = taskList.get(position)
    holder.render(task)
  }

  /** кількість елементів, що треба відмалювати **/
  override fun getItemCount(): Int {
    return taskList.size
  }

  fun removeAt(position: Int): Task {
    val deletedTask = taskList.removeAt(position)
    notifyItemRemoved(position)
    return deletedTask
  }

  /** Клас контейнера одного елемента на екрані який вміє відмалювати один елемент **/
  class TaskViewHolder(val view: View): RecyclerView.ViewHolder(view){

    private val tvTaskTitle: TextView? = view.findViewById<TextView>(R.id.task_name)
    private val tvTaskCategory: TextView? = view.findViewById<TextView>(R.id.task_category)
    private val imageTaskPriority: ImageView? = view.findViewById<ImageView>(R.id.ic_priority)

    fun render(task: Task){
      tvTaskTitle?.text = task.title
      tvTaskCategory?.text = task.category.name
      val icon = when(task.priority){
        TaskPriority.Low -> R.drawable.ic_arrow_down_24dp
        TaskPriority.Medium -> R.drawable.ic_arrow_right_24dp
        TaskPriority.High -> R.drawable.ic_arrow_up_24dp
      }
      imageTaskPriority?.setImageResource(icon)
    }
  }

}