package com.serg.simplewidgetforecast.data.provider

import com.serg.simplewidgetforecast.data.db.Coord
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse

interface LocationProvider {
    suspend fun hasLocationChanged(lastLocation: CurrentWeatherResponse): Boolean
    suspend fun getPreferredLocationString(): Any
    //suspend fun getPreferredLocationCoord(): Coord
}