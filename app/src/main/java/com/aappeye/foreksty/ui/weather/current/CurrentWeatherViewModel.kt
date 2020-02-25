package com.aappeye.foreksty.ui.weather.current

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.ui.base.VIEW_MODEL
import com.aappeye.foreksty.ui.base.WeatherViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrentWeatherViewModel(
    val forecastRepository: ForecastRepository,
    settingsProvider: SettingsProvider
) : WeatherViewModel(forecastRepository, settingsProvider) {

    val weather = MutableLiveData<CurrentWeatherEntry>()

    override fun fetchData() {
        fetchLocationData()
        compositeDisposable.add(forecastRepository.getCurrentWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weather.postValue(it)
            }, {
                Log.e(VIEW_MODEL, "onError: ", it)
            })
        )
    }
}
