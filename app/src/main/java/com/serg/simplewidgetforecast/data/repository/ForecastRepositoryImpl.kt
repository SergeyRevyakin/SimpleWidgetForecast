package com.serg.simplewidgetforecast.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.serg.simplewidgetforecast.data.response.Coord
import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse
import com.serg.simplewidgetforecast.data.db.CurrentWeatherDao
import com.serg.simplewidgetforecast.data.network.WeatherNetworkDataSource
import com.serg.simplewidgetforecast.data.provider.LocationProvider
import kotlinx.coroutines.*
import org.threeten.bp.Instant

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
            //Log.e("UPSERTED","${it.main.feelsLike}")
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
        val response = currentWeatherDao.getWeather().value
        if (response==null){//||locationProvider.hasLocationChanged(response)){
            fetchCurrentWeather()
            return
        }
//        if (response==null||isFetchCurrentNeeded(Instant.ofEpochSecond(response))){
//            fetchCurrentWeather()
//        }
    }

    private suspend fun fetchCurrentWeather() {
        when (locationProvider.getPreferredLocationString()) {
            is String -> {
                if (locationProvider.getPreferredLocationString().equals(null)) {
                    weatherNetworkDataSource.fetchCurrentWeather("Malinovka")
                }
                weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString() as String)
            }
            is Coord -> weatherNetworkDataSource
                .fetchCurrentWeather(locationProvider.getPreferredLocationString() as Coord)
            else -> Log.e("LocationProvider", "Incorrect type of locationProvider")
//        Locale.getDefault().language
        }
    }

    private fun isFetchCurrentNeeded(lastFetchTime: Instant): Boolean {
        val thirtyMinutesAgo = Instant.now().minusSeconds(180)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}