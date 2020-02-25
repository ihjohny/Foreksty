package com.aappeye.foreksty.ui.weather.today

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.ui.base.VIEW_MODEL
import com.aappeye.foreksty.ui.base.WeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodayWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
) : WeatherViewModel(forecastRepository, settingsProvider) {

    val todayWeather = MutableLiveData<DailyWeatherEntry>()
    val hourlyWeatherList = MutableLiveData<List<HourlyWeatherEntry>>()

    override fun fetchData() {
        fetchLocationData()
        compositeDisposable.add(forecastRepository.getTodayWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                todayWeather.postValue(it)
            }, {
                Log.e(VIEW_MODEL, "onError: ", it)
            }))
        compositeDisposable.add(forecastRepository.getHourlyWeatherList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hourlyWeatherList.postValue(it)
            }, {
                Log.e(VIEW_MODEL, "onError: ", it)
            }))
    }

}
