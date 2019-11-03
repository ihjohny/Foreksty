package com.aappeye.foreksty.data.network

import androidx.lifecycle.LiveData
import com.aappeye.foreksty.data.network.response.WeatherResponse

interface WeatherNetworkDataSource {
    val downloadedWeather : LiveData<WeatherResponse>
    suspend fun fetchWeather(
        location: String,
        lang: String
    )
}
