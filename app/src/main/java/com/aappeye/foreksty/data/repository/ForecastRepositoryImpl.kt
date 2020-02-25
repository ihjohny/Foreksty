package com.aappeye.foreksty.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val settingsProvider: SettingsProvider,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedWeather.observeForever { newWeather ->
                presistFetchedWeather(newWeather)
            }
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()
        if (lastWeatherLocation == null || isUpdateFreq(lastWeatherLocation.zonedDateTime)) {
            fetchWeather()
        }
    }

    private fun presistFetchedWeather(fetchedWeather: WeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
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
    }

    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getHourlyWeatherList(): LiveData<List<HourlyWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext hourlyWeatherDao.getHourlyWeather()
        }
    }

    override suspend fun getTodayWeather(): LiveData<DailyWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext dailyWeatherDao.getTodayWeather()
        }
    }

    override suspend fun getDailyWeatherList(): LiveData<List<DailyWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext dailyWeatherDao.getDailyWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocationEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private suspend fun fetchWeather() {
        weatherNetworkDataSource.fetchWeather(
            locationProvider.getPreferredLocationString(),
            settingsProvider.getPreferredLanguage()
        )
    }

    private fun isUpdateFreq(lastFetchTime: ZonedDateTime): Boolean {
        val updateFrequency = settingsProvider.getPreferredUpdateFrequency().toLong()
        val minutesAgo = ZonedDateTime.now().minusMinutes(updateFrequency) //update frequency
        return lastFetchTime.isBefore(minutesAgo)
    }

}