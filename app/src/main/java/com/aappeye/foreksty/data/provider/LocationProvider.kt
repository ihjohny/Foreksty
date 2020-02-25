package com.aappeye.foreksty.data.provider

import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocationEntry): Boolean
    suspend fun getPreferredLocationString(): String

}
