package com.serg.simplewidgetforecast.internal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.serg.simplewidgetforecast.data.db.Weather
import java.util.*

class WeatherConverter {

    @TypeConverter
    fun listToJson(value: List<Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()

}