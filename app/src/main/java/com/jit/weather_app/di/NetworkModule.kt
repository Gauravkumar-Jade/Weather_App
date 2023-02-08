package com.jit.weather_app.di

import com.jit.weather_app.BuildConfig
import com.jit.weather_app.network.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPI(retrofit: Retrofit):WeatherApi{
        return retrofit.create(WeatherApi::class.java)
    }
}