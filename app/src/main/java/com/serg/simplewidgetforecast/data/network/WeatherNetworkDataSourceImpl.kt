package com.serg.simplewidgetforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.data.db.Coord
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(coord: Coord) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherByCoordAsync(coord.lat, coord.lon)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e:NoConnectivityException){
            Log.e("Connectivity", "No internet connection. ", e)
        }
    }

    override suspend fun fetchCurrentWeather(city: String) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherByCityAsync(city)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e:NoConnectivityException){
            Log.e("Connectivity", "No internet connection. ", e)
        }
    }
}