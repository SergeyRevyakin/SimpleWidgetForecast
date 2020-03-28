package com.serg.simplewidgetforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.data.DB.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(latitude: Double, longitude: Double) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherAsync(latitude,longitude)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e:NoConnectivityException){
            Log.e("Connectivity", "No internet connection. ", e)
        }

    }
}