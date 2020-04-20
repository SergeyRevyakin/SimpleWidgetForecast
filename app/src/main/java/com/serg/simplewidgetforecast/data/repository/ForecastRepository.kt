package com.serg.simplewidgetforecast.data.repository

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherResponse>
}