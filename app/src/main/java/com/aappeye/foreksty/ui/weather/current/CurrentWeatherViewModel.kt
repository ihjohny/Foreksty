 package com.aappeye.foreksty.ui.weather.current

import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred
import com.aappeye.foreksty.ui.base.WeatherViewModel


 class CurrentWeatherViewModel(
     private val forecastRepository: ForecastRepository,
     settingsProvider: SettingsProvider
) : WeatherViewModel(forecastRepository, settingsProvider) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}
