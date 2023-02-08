package com.jit.weather_app.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jit.weather_app.BuildConfig
import com.jit.weather_app.model.WeatherData
import com.jit.weather_app.network.WeatherApi
import com.jit.weather_app.utils.NetworkResult
import com.jit.weather_app.utils.NetworkUtils
import org.json.JSONObject
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val context: Context, private val weatherApi: WeatherApi) {

    private val _weatherResult = MutableLiveData<NetworkResult<WeatherData>>()
    val weatherResult:LiveData<NetworkResult<WeatherData>> get() = _weatherResult

    suspend fun getRepositoryData(city:String){

        if(NetworkUtils.isInternetAvailable(context)){
            try {
                val response = weatherApi.getWeatherData(city,BuildConfig.API_KEY)

                if(response.isSuccessful && response.body() != null){
                    _weatherResult.postValue(NetworkResult.Success(response.body()!!))
                }
                else if (response.errorBody() != null){
                    val errorObject = JSONObject(response.errorBody()!!.charStream().readText())
                    _weatherResult.postValue(NetworkResult.Error(errorObject.getString("message")))



                }else{
                    _weatherResult.postValue(NetworkResult.Error("Something Went Wrong"))
                }

            }catch (e: Exception){
                Log.i("WEATHER_ERROR",e.toString())
            }
        }else{
            _weatherResult.postValue(NetworkResult.Error("No Network or Internet Connection"))
        }
    }
}