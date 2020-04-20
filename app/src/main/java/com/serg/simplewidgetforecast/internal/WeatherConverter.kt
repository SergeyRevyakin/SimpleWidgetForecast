package com.serg.simplewidgetforecast.internal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.serg.simplewidgetforecast.data.response.Weather

class WeatherConverter {

    @TypeConverter
    fun listToJson(value: List<Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()

}