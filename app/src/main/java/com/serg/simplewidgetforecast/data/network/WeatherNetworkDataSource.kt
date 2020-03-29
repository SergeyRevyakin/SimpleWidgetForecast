package com.serg.simplewidgetforecast.data.network

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    )
}