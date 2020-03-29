package com.serg.simplewidgetforecast.data.db

import androidx.room.*
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0


data class CurrentWeatherResponse(
    val base: String,
//    @Embedded(prefix = "clouds_")
    val clouds: Clouds,
    val cod: Int,
//    @Embedded(prefix = "coord_")
    val coord: Coord,
    val dt: Int,
    //val iD: Int,
    //@Embedded(prefix = "main_")
    @SerializedName("main")
    val currentWeatherEntry: CurrentWeatherEntry,
    val name: String,
//    @Embedded(prefix = "rain_")
    val rain: Rain?,
//    @Embedded(prefix = "snow_")
    val snow: Snow?,
//    @Embedded(prefix = "sys_")
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
//    @TypeConverters(Converters::class)
//    @Embedded(prefix = "weather_")
//   // @Ignore
    val weather: List<Weather>,
//    @Embedded(prefix = "wind_")
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}

data class Rain(
    @SerializedName("1h")
    val h1: Double?,
    @SerializedName("3h")
    val h3: Double?
)

data class Snow(
    @SerializedName("1h")
    val h1: Double?,
    @SerializedName("3h")
    val h3: Double?
)

data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    //val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)

