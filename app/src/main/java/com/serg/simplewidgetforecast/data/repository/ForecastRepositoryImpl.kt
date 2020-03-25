package com.serg.simplewidgetforecast.data.repository

import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherDB
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherDao
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherEntry
import com.serg.simplewidgetforecast.data.network.CurrentWeatherResponse
import com.serg.simplewidgetforecast.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->

        }
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        TODO("Not yet implemented")
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
        }

    }
}