package com.jit.weather_app.network

import com.jit.weather_app.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?&units=metric")
    suspend fun getWeatherData(@Query("q") city: String, @Query("appid") api_key: String):Response<WeatherData>
}