package com.serg.simplewidgetforecast.data.network

import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.data.response.Coord
import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        city: String
    )
    suspend fun fetchCurrentWeather(
        coord: Coord
    )

   // suspend fun fetchCurrentWeather(preferredLocationString: Any)
}