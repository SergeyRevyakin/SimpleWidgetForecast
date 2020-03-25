package com.serg.simplewidgetforecast.data.repository

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>

}