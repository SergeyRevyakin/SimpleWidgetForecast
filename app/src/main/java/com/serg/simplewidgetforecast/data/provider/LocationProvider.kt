package com.serg.simplewidgetforecast.data.provider

import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(lastLocation: CurrentWeatherResponse): Boolean
    suspend fun getPreferredLocationString(): String
}