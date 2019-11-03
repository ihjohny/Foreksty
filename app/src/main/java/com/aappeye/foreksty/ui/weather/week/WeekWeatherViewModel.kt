package com.aappeye.foreksty.ui.weather.week

import androidx.lifecycle.ViewModel;
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred
import com.aappeye.foreksty.ui.base.WeatherViewModel

class WeekWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val settingsProvider: SettingsProvider
) : WeatherViewModel(forecastRepository, settingsProvider) {

    val dailyWeatherList by lazyDeferred {
        forecastRepository.getDailyWeatherList()
    }
}
