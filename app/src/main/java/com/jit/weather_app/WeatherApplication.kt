package com.jit.weather_app

import android.app.Application
import com.jit.weather_app.di.DaggerWeatherComponent
import com.jit.weather_app.di.WeatherComponent

class WeatherApplication:Application() {

    lateinit var weatherComponent: WeatherComponent

    override fun onCreate() {
        super.onCreate()

        weatherComponent = DaggerWeatherComponent.factory().create(this)

    }
}