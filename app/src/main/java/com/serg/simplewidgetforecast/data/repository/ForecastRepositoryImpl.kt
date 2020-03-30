package com.serg.simplewidgetforecast.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse
import com.serg.simplewidgetforecast.data.db.CurrentWeatherDao
import com.serg.simplewidgetforecast.data.network.WeatherNetworkDataSource
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
            Log.e("UPSERTED","${it.main.feelsLike}")
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out CurrentWeatherResponse> {
        initWeatherData()
        return withContext(Dispatchers.IO) {
            Log.e("DAO","${currentWeatherDao.getWeather().value?.main?.feelsLike}")
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
            Log.e("UPSERT","${fetchedWeather.main.feelsLike}")

        }

    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(50.0,60.0)
//        Locale.getDefault().language
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}