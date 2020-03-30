package com.serg.simplewidgetforecast.data.repository

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse
import com.serg.simplewidgetforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out CurrentWeatherResponse>
}