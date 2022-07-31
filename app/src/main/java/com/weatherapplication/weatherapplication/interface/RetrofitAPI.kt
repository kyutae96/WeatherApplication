package com.weatherapplication.weatherapplication.`interface`

import com.weatherapplication.weatherapplication.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPI {
    @GET("data/2.5/forecast")
    fun getWeather(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Query("appid") appid: String?) :
            Call<WeatherResponse>
}