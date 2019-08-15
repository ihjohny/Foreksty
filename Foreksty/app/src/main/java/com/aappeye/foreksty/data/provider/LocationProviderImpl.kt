package com.aappeye.foreksty.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.aappeye.foreksty.data.db.entity.WeatherLocation
import com.aappeye.foreksty.internal.LocationPermissionNotGrantedException
import com.aappeye.foreksty.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION ="CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }
        catch (e: LocationPermissionNotGrantedException){
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
/*        if(isUsingDeviceLocation()){
            try{
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getCustomLocationName()}"
                    return "${deviceLocation.latitude},${deviceLocation.longitude}"
            }
            catch (e: LocationPermissionNotGrantedException){
                return "${getCustomLocationName()}"
            }
        }
        else{
            return "${getCustomLocationName()}"
        }*/
        try{
            val deviceLocation = getLastDeviceLocation().await()
                ?: return "23.127487,90.750908"
            return "${deviceLocation.latitude},${deviceLocation.longitude}"
        }
        catch (e: LocationPermissionNotGrantedException){
            return "23.127487,90.750908"
        }


    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean{
        if(!isUsingDeviceLocation())
            return false
        val deviceLocation = getLastDeviceLocation().await()?: return false
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.latitude) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.longitude) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean{
        if(!isUsingDeviceLocation()){
            val customLocationName = getCustomLocationName()
            //return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String?{
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location>{
        return if(hasLocationPermission()) {
            fusedLocationProviderClient.lastLocation.asDeferred()
        }
        else{
            throw LocationPermissionNotGrantedException()
        }
    }

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

}