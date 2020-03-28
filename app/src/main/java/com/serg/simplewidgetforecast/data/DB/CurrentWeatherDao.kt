package com.serg.simplewidgetforecast.data.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherResponse) {

    }

    @Query(value = "select * from current_weather where id = $KEY")
    fun getWeather(): LiveData<CurrentWeatherResponse>
}