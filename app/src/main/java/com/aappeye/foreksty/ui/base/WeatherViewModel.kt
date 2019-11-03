package com.aappeye.foreksty.ui.base

import androidx.lifecycle.ViewModel
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.provider.UNIT_SYSTEM_DEF
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
): ViewModel() {

    private val unitSystem = settingsProvider.getPreferredUnitSystem()
    val isMetricUnit: Boolean
        get() = unitSystem == UNIT_SYSTEM_DEF

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}