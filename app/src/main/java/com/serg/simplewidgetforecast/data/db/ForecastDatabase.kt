package com.serg.simplewidgetforecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.WeatherConverter


@Database(
    entities = [CurrentWeatherResponse::class],
    version = 1
)
@TypeConverters(
    value = [(WeatherConverter::class)]
)

abstract class ForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also { instance = it }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db"
            ).build()
    }
}