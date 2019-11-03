package com.aappeye.foreksty.ui.weather.today

import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred
import com.aappeye.foreksty.ui.base.WeatherViewModel

class TodayWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
): WeatherViewModel(forecastRepository, settingsProvider) {

    val todayWeather by lazyDeferred{
        forecastRepository.getTodayWeather()
    }
    val hourlyWeatherList by lazyDeferred {
        forecastRepository.getHourlyWeatherList()
    }
}
