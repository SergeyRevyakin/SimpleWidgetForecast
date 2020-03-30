package com.serg.simplewidgetforecast.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    )
}