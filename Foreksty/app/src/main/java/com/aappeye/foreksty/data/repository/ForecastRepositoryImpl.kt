package com.aappeye.foreksty.data.repository

import androidx.lifecycle.LiveData
import com.aappeye.foreksty.data.db.CurrentWeatherDao
import com.aappeye.foreksty.data.db.DailyWeatherDao
import com.aappeye.foreksty.data.db.HourlyWeatherDao
import com.aappeye.foreksty.data.db.WeatherLocationDao
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocation
import com.aappeye.foreksty.data.network.WeatherNetworkDataSource
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.aappeye.foreksty.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
): ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedWeather.observeForever { newWeather ->
                presistFetchedWeather(newWeather)
            }
        }
    }

    override suspend fun fetchWeather(lati: String, long: String, lang: String){
        weatherNetworkDataSource.fetchWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun presistFetchedWeather(fetchedWeather: WeatherResponse){
        GlobalScope.launch(Dispatchers.IO){
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            hourlyWeatherDao.deleteOldEntries()
            hourlyWeatherDao.insert(fetchedWeather.hourlyWeatherEntries.Hourlyentries)
            dailyWeatherDao.deleteOldEntries()
            dailyWeatherDao.insert(fetchedWeather.dailyWeatherEntries.dailyentries)
            // weatherLocationDao.upsert(fetchedWeather.location)
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

    override suspend fun getDailyWeatherList(): LiveData<List<DailyWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext dailyWeatherDao.getDailyWeather()
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

}