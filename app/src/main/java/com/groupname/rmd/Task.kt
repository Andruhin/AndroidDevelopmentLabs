package com.groupname.rmd

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
  val id: String,
  val title: String,
  val category: TaskCategory,
  val priority: TaskPriority,
  val hasLocation: Boolean
): Parcelable
