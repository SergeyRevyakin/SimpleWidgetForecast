package com.serg.simplewidgetforecast.data.provider

import com.serg.simplewidgetforecast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}