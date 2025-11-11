package com.groupname.rmd

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
  val id: String,
  val description: String,
  val address: String,
  val latLng: LatLng?
): Parcelable
