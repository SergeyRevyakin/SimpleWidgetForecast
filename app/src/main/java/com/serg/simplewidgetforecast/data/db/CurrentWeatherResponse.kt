package com.serg.simplewidgetforecast.data.db

import androidx.room.*
import com.serg.simplewidgetforecast.internal.WeatherConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    val base: String,
    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Long,
    //val iD: Int,
    @Embedded(prefix = "main_")
    val main: Main,
    val name: String,
    @Embedded(prefix = "rain_")
    val rain: Rain?,
    @Embedded(prefix = "snow_")
    val snow: Snow?,
    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    @TypeConverters(WeatherConverter::class)
//    @Embedded(prefix = "weather_")
//    @Ignore
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind
) {
    @PrimaryKey(autoGenerate = false)
    var primaryKey: Int = CURRENT_WEATHER_ID
//    val zonedDateTime: ZonedDateTime
//        get() {
//            val instant = Instant.ofEpochSecond(dt)
//            val zoneId = ZoneId.of(timezone.toString())
//            return ZonedDateTime.ofInstant(instant, zoneId)
//        }
}


