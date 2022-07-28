package com.weatherapplication.weatherapplication.builder

import com.weatherapplication.weatherapplication.`interface`.RetrofitAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api : RetrofitAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitAPI::class.java)
    }
}