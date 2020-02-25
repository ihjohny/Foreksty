package com.aappeye.foreksty.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import com.aappeye.foreksty.internal.LocationPermissionNotGrantedException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import kotlin.Exception

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"
const val DEFAULT_LOCATION = "23.7811055,90.400918"


class LocationProviderImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override fun hasLocationChanged(lastWeatherLocation: WeatherLocationEntry): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override fun getPreferredLocationString(): String {
        var location = DEFAULT_LOCATION
        try {
            getLastDeviceLocation().addOnSuccessListener { result ->
                location = "${result.latitude},${result.longitude}"
            }.addOnFailureListener { exception ->
                location = DEFAULT_LOCATION
            }
        } catch (e: Exception) {
            location = DEFAULT_LOCATION
        }
        return location
    }

    private fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocationEntry): Boolean {
        val comparisonThreshold = 0.03
        var isChanged = false
        if (!isUsingDeviceLocation())
            return isChanged
        getLastDeviceLocation().addOnSuccessListener {
            isChanged =
                (Math.abs(it.latitude - lastWeatherLocation.latitude) > comparisonThreshold &&
                        Math.abs(it.longitude - lastWeatherLocation.longitude) > comparisonThreshold)
        }.addOnFailureListener {
            isChanged = false
        }
        return isChanged
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocationEntry): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            //return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(LANGUAGE, null)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Task<Location> {
        return if (hasLocationPermission()) {
            fusedLocationProviderClient.lastLocation
        } else {
            throw LocationPermissionNotGrantedException()
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}