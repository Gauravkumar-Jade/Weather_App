package com.jit.weather_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jit.weather_app.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModelFactory @Inject constructor(private val repository: WeatherRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Not ViewModel")
    }
}