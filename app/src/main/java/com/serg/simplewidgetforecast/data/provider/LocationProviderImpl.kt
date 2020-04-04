package com.serg.simplewidgetforecast.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.serg.simplewidgetforecast.data.db.Coord
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.LocationPermissionNonGrantedException
import com.serg.simplewidgetforecast.internal.asDeferred
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferencesProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastLocation: CurrentWeatherResponse): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastLocation)
        } catch (e: LocationPermissionNonGrantedException) {
            false
        }
        return deviceLocationChanged || hasCustomLocationChanged(lastLocation)
    }

    override suspend fun getPreferredLocationString(): Any {

        if (isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                return Coord(deviceLocation.latitude, deviceLocation.longitude)
                //"lat=${deviceLocation.latitude}&lon=${deviceLocation.longitude}"
            } catch (e:LocationPermissionNonGrantedException) {
                return "${getCustomLocationName()}"
            }
        }
        else return "${getCustomLocationName()}"
    }

    private suspend fun hasCustomLocationChanged(lastLocation: CurrentWeatherResponse): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName!=lastLocation.name
    }

    private suspend fun hasDeviceLocationChanged(lastLocation: CurrentWeatherResponse): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - lastLocation.coord.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastLocation.coord.lon) > comparisonThreshold

    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    //@SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNonGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

}