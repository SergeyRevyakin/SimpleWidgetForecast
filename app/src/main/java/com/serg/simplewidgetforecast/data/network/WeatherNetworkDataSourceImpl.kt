package com.serg.simplewidgetforecast.data.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.serg.simplewidgetforecast.R
import com.serg.simplewidgetforecast.data.response.Coord
import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.HttpException

class WeatherNetworkDataSourceImpl(
    private val openWeatherMapApiService: OpenWeatherMapApiService,
    val context: Context
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(coord: Coord) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherByCoordAsync(coord.lat, coord.lon)

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection. ", e)
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    R.string.no_internet_connection,
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (ex: HttpException) {
            Log.e("Connectivity", "No internet connection. ", ex)
        }
    }

    override suspend fun fetchCurrentWeather(city: String) {
        try {
            val fetchedCurrentWeather = openWeatherMapApiService
                .getCurrentWeatherByCityAsync(city)

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            //Log.e("Connectivity", "No internet connection. ", e)
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    R.string.no_internet_connection,
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (ex: HttpException) {
            //Log.e("Connectivity", "No internet connection. ", ex)
            //Dispatchers.IO.run {  }
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    R.string.custom_location_api_error,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
}