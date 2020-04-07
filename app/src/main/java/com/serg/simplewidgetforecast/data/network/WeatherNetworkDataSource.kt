package com.serg.simplewidgetforecast.data.network

import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.data.db.Coord
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse

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