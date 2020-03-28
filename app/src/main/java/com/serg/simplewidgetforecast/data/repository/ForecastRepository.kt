package com.serg.simplewidgetforecast.data.repository

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherResponse>

}