package com.groupname.rmd

data class Task(
  val id: String,
  val title: String,
  val category: TaskCategory,
  val priority: TaskPriority,
  val hasLocation: Boolean
)
