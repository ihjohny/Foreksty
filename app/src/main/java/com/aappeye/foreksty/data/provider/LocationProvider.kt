package com.aappeye.foreksty.data.provider

import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry

interface LocationProvider {
    fun hasLocationChanged(lastWeatherLocation: WeatherLocationEntry): Boolean
    fun getPreferredLocationString(): String

}
