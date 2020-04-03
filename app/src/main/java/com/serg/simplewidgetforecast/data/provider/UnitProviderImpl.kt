package com.serg.simplewidgetforecast.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.serg.simplewidgetforecast.internal.UnitSystem

class UnitProviderImpl(context: Context) : PreferencesProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString("UNIT_SYSTEM", UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}