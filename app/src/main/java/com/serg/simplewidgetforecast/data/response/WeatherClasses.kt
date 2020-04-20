package com.serg.simplewidgetforecast.data.response

import com.google.gson.annotations.SerializedName

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

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