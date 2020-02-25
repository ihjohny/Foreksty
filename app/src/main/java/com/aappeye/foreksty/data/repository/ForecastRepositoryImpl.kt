package com.aappeye.foreksty.data.repository

import com.aappeye.foreksty.data.db.CurrentWeatherDao
import com.aappeye.foreksty.data.db.DailyWeatherDao
import com.aappeye.foreksty.data.db.HourlyWeatherDao
import com.aappeye.foreksty.data.db.WeatherLocationDao
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import com.aappeye.foreksty.data.network.WeatherNetworkDataSource
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.aappeye.foreksty.data.provider.LocationProvider
import com.aappeye.foreksty.data.provider.SettingsProvider
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

const val REPOSITORY = "Repository"

class ForecastRepositoryImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val settingsProvider: SettingsProvider,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    override fun fetchWeather(): Observable<WeatherResponse> {
        return weatherNetworkDataSource.fetchWeather(
            locationProvider.getPreferredLocationString(),
            settingsProvider.getPreferredLanguage()
        )
    }

    override fun presistFetchedWeather(fetchedWeather: WeatherResponse) {
        currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        hourlyWeatherDao.deleteOldEntries()
        hourlyWeatherDao.insert(fetchedWeather.hourlyWeatherEntries.hourlyentries)
        dailyWeatherDao.deleteOldEntries()
        dailyWeatherDao.insert(fetchedWeather.dailyWeatherEntries.dailyentries)
        weatherLocationDao.upsert(
            WeatherLocationEntry(
                fetchedWeather.latitude,
                fetchedWeather.longitude,
                fetchedWeather.timezone,
                fetchedWeather.currentWeatherEntry.time
            )
        )
    }

    override fun getCurrentWeather(): Observable<CurrentWeatherEntry> {
        return currentWeatherDao.getWeather()
    }

    override fun getHourlyWeatherList(): Observable<List<HourlyWeatherEntry>> {
        return hourlyWeatherDao.getHourlyWeather()
    }

    override fun getTodayWeather(): Observable<DailyWeatherEntry> {
        return dailyWeatherDao.getTodayWeather()
    }

    override fun getDailyWeatherList(): Observable<List<DailyWeatherEntry>> {
        return dailyWeatherDao.getDailyWeather()
    }

    override fun getWeatherLocation(): Single<WeatherLocationEntry> {
        return weatherLocationDao.getLocation()
    }

}