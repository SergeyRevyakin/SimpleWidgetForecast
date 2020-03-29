package com.serg.simplewidgetforecast.data.db.unitlocalized

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "feelsLike")
    override val feelsLike: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Int,
    @ColumnInfo(name = "pressure")
    override val pressure: Int,
    @ColumnInfo(name = "temp")
    override val temp: Double,
    @ColumnInfo(name = "tempMax")
    override val tempMax: Double,
    @ColumnInfo(name = "tempMin")
    override val tempMin: Double
) : UnitSpecificCurrentWeatherEntry