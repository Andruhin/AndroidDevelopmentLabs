package com.groupname.rmd

enum class Priority { LOW, MEDIUM, HIGH }

data class Task(
  val title: String,
  val category: String,
  val priority: Priority,
  val attachToLocation: Boolean,
)
