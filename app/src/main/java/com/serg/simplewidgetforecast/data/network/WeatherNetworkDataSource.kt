package com.serg.simplewidgetforecast.data.network

import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    )
}