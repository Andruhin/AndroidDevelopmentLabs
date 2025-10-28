package com.groupname.rmd

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

  private val baseUrl = "https://api.privatbank.ua/p24api/"

  private val client = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val apiService = client.create(ApiService::class.java)
}