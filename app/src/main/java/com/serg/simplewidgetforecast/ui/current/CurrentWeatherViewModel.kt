package com.serg.simplewidgetforecast

import androidx.lifecycle.ViewModel
import com.serg.simplewidgetforecast.data.repository.ForecastRepository
import com.serg.simplewidgetforecast.internal.UnitSystem
import com.serg.simplewidgetforecast.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}
