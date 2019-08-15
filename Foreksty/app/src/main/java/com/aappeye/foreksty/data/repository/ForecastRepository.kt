package com.aappeye.foreksty.data.repository

import androidx.lifecycle.LiveData
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocation

interface ForecastRepository {
    suspend fun fetchWeather(lati: String, long: String, lang: String)
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getHourlyWeatherList(): LiveData<List<HourlyWeatherEntry>>
    suspend fun getDailyWeatherList(): LiveData<List<DailyWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}