package com.serg.simplewidgetforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serg.simplewidgetforecast.data.db.CURRENT_WEATHER_ID



@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherResponse)

    @Query("select * FROM current_weather")
    fun getWeather(): LiveData<CurrentWeatherResponse>

//    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
//    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}