package com.serg.simplewidgetforecast.ui.current

import androidx.lifecycle.ViewModel
import com.serg.simplewidgetforecast.data.provider.UnitProvider
import com.serg.simplewidgetforecast.data.repository.ForecastRepository
import com.serg.simplewidgetforecast.internal.UnitSystem
import com.serg.simplewidgetforecast.internal.lazyDeferred
import kotlinx.coroutines.*

class CurrentWeatherViewModel(
    unitProvider:UnitProvider,
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    private val isMetric: Boolean
        get() = (unitSystem==UnitSystem.METRIC)

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}
