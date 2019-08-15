 package com.aappeye.foreksty.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.internal.lazyDeferred
import com.aappeye.foreksty.ui.base.WeatherViewModel


 class CurrentWeatherViewModel(
     private val forecastRepository: ForecastRepository
) : WeatherViewModel(forecastRepository) {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}
