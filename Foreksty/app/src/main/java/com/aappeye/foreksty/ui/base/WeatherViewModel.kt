package com.aappeye.foreksty.ui.base

import androidx.lifecycle.ViewModel
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository
): ViewModel() {
    val weatherLocation by lazyDeferred{
        forecastRepository.getWeatherLocation()
    }
}