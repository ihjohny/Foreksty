package com.aappeye.foreksty

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.aappeye.foreksty.data.db.WeatherDatabase
import com.aappeye.foreksty.data.network.*
import com.aappeye.foreksty.data.provider.LocationProvider
import com.aappeye.foreksty.data.provider.LocationProviderImpl
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.provider.SettingsProviderImpl
import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.data.repository.ForecastRepositoryImpl
import com.aappeye.foreksty.ui.weather.current.CurrentWeatherViewModelFactory
import com.aappeye.foreksty.ui.weather.today.TodayWeatherViewModelFactory
import com.aappeye.foreksty.ui.weather.week.WeekWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForekstyApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preference,false)
    }
}
