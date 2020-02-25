package com.aappeye.foreksty.ui.weather.week

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.ui.base.VIEW_MODEL
import com.aappeye.foreksty.ui.base.WeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeekWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
) : WeatherViewModel(forecastRepository, settingsProvider) {

    val dailyWeatherList = MutableLiveData<List<DailyWeatherEntry>>()

    override fun fetchData() {
        fetchLocationData()
        compositeDisposable.add(forecastRepository.getDailyWeatherList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dailyWeatherList.postValue(it)
            }, {
                Log.e(VIEW_MODEL, "onError: ", it)
            }))
    }
}
