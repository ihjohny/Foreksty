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


class ForekstyApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForekstyApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().hourlyWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().dailyWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiWeatherService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<SettingsProvider>() with singleton { SettingsProviderImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance(), instance(), instance(), instance() ) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { TodayWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { WeekWeatherViewModelFactory(instance(), instance()) }
  //      bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preference,false)
    }
}
