package com.jit.weather_app.di

import android.content.Context
import com.jit.weather_app.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface WeatherComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface factory{
        fun create(@BindsInstance context: Context):WeatherComponent
    }
}