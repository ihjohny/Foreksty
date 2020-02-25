package com.aappeye.foreksty.data.network

import com.aappeye.foreksty.data.network.response.WeatherResponse
import io.reactivex.Observable

interface WeatherNetworkDataSource {
    fun fetchWeather(
        location: String,
        lang: String
    ): Observable<WeatherResponse>
}
