package com.jit.weather_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jit.weather_app.model.WeatherData
import com.jit.weather_app.repository.WeatherRepository
import com.jit.weather_app.utils.NetworkResult
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository):ViewModel() {

    fun getWeatherData(city: String){
        viewModelScope.launch {
            repository.getRepositoryData(city)
        }
    }

    val weatherData: LiveData<NetworkResult<WeatherData>> get() = repository.weatherResult
}