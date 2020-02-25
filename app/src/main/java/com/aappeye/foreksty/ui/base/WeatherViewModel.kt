package com.aappeye.foreksty.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.provider.UNIT_SYSTEM_DEF
import com.aappeye.foreksty.data.repository.ForecastRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.ZonedDateTime

const val VIEW_MODEL = "ViewModel"

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val settingsProvider: SettingsProvider
) : ViewModel() {

    private val unitSystem = settingsProvider.getPreferredUnitSystem()
    val isMetricUnit: Boolean
        get() = unitSystem == UNIT_SYSTEM_DEF
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val weatherLocation = MutableLiveData<WeatherLocationEntry>()

    private fun checkData() {
        compositeDisposable.add(
            forecastRepository.getWeatherLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    if (isUpdateFreq(it.zonedDateTime)) {
                        fetch()
                    }
                }, {
                    fetch()
                })
        )
    }

    private fun fetch() {
        compositeDisposable.add(
            forecastRepository.fetchWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    forecastRepository.presistFetchedWeather(it)
                },{
                    Log.e(VIEW_MODEL, "Data fetch failed ", it)
                })
        )
    }

    protected fun fetchLocationData() {
        checkData()
        compositeDisposable.add(
            forecastRepository.getWeatherLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherLocation.postValue(it)
                }, {
                    Log.e(VIEW_MODEL, "onError: ", it)
                })
        )
    }

    abstract fun fetchData()

    private fun isUpdateFreq(lastFetchTime: ZonedDateTime): Boolean {
        val updateFrequency = settingsProvider.getPreferredUpdateFrequency().toLong()
        val minutesAgo = ZonedDateTime.now().minusMinutes(updateFrequency)
        return lastFetchTime.isBefore(minutesAgo)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}