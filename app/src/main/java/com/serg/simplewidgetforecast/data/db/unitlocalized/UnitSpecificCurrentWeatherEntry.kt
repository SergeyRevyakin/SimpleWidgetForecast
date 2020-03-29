package com.serg.simplewidgetforecast.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val feelsLike: Double
    val humidity: Int
    val pressure: Int
    val temp: Double
    val tempMax: Double
    val tempMin: Double
}