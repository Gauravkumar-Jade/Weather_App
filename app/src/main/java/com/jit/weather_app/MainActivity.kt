package com.jit.weather_app

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jit.weather_app.databinding.ActivityMainBinding
import com.jit.weather_app.utils.NetworkResult
import com.jit.weather_app.viewmodel.WeatherViewModel
import com.jit.weather_app.viewmodel.WeatherViewModelFactory
import com.squareup.picasso.Picasso
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        (application as WeatherApplication).weatherComponent.inject(this)

        weatherViewModel = ViewModelProvider(this,weatherViewModelFactory).get(WeatherViewModel::class.java)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading..")

        binding.ivEnterButton.setOnClickListener {
            onGetWeather()
        }
    }

    private fun onGetWeather() {
        val city = binding.etCityName.text.toString()

        if(city.isEmpty()){
            Toast.makeText(this,"Enter City Name !", Toast.LENGTH_SHORT).show()
            return
        }
        progressDialog.show()

        weatherViewModel.getWeatherData(city)

            weatherViewModel.weatherData.observe(this) {
                when (it) {

                    is NetworkResult.Success -> {
                        progressDialog.dismiss()
                        val temp = it.data!!.main.temp
                        val description = it.data.weather.get(0).description
                        val iconId = it.data.weather.get(0).icon

                        val iconUrl = "https://openweathermap.org/img/wn/${iconId}@2x.png"

                        binding.apply {
                            tvWeatherDescription.text = description
                            tvWeatherInfo.text = String.format("%.2f", temp).plus("°C")
                            Picasso.get().load(iconUrl).into(ivWeatherIcon)
                        }

                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val timestamp = Timestamp(System.currentTimeMillis())

                        binding.tvWeatherTime.text = dateFormat.format(timestamp)

                    }

                    is NetworkResult.Error -> {
                        progressDialog.dismiss()
                        binding.tvWeatherDescription.text = it.message.toString()
                        binding.tvWeatherInfo.text = ""
                        binding.ivWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_error_))
                    }
                }

            }
    }
}