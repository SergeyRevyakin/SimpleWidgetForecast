package com.serg.simplewidgetforecast.ui.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.serg.simplewidgetforecast.data.provider.UnitProvider
import com.serg.simplewidgetforecast.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(unitProvider, forecastRepository) as T
    }
}