package com.aappeye.foreksty.data.repository

import androidx.lifecycle.LiveData
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
    suspend fun getHourlyWeatherList(): LiveData<List<HourlyWeatherEntry>>
    suspend fun getTodayWeather(): LiveData<DailyWeatherEntry>
    suspend fun getDailyWeatherList(): LiveData<List<DailyWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocationEntry>

}