package com.aappeye.foreksty.ui.weather.today

import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.ui.base.WeatherViewModel

class TodayWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
): WeatherViewModel(forecastRepository, settingsProvider) {

}
