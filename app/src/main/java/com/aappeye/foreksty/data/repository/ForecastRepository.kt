package com.aappeye.foreksty.data.repository

import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import com.aappeye.foreksty.data.network.response.WeatherResponse
import io.reactivex.Observable
import io.reactivex.Single

interface ForecastRepository {

    fun fetchWeather(): Observable<WeatherResponse>
    fun presistFetchedWeather(fetchedWeather: WeatherResponse)

    fun getCurrentWeather(): Observable<CurrentWeatherEntry>
    fun getHourlyWeatherList(): Observable<List<HourlyWeatherEntry>>
    fun getTodayWeather(): Observable<DailyWeatherEntry>
    fun getDailyWeatherList(): Observable<List<DailyWeatherEntry>>
    fun getWeatherLocation(): Single<WeatherLocationEntry>

}