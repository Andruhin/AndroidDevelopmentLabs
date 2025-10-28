package com.groupname.rmd.model

import com.google.gson.annotations.SerializedName

data class Currency(
  @SerializedName("ccy")
  val currency: String,
  @SerializedName("base_ccy")
  val baseCurrency: String,
  @SerializedName("buy")
  val buyRate: String,
  @SerializedName("sale")
  val saleRate: String,
)
