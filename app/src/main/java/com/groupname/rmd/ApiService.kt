package com.groupname.rmd

import com.groupname.rmd.model.Currency
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

  @GET("pubinfo?exchange&coursid=5")
  fun getCurrencyRate(): Call<List<Currency>>

}