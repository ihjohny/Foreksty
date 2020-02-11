package com.aappeye.foreksty.di.module

import androidx.lifecycle.ViewModelProvider
import com.aappeye.foreksty.ui.weather.current.CurrentWeatherViewModelFactory
import com.aappeye.foreksty.ui.weather.today.TodayWeatherViewModelFactory
import com.aappeye.foreksty.ui.weather.week.WeekWeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideCurrentWeatherViewModelFactory(currentWeatherViewModelFactory: CurrentWeatherViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun provideTodayWeatherViewModelFactory(todayWeatherViewModelFactory: TodayWeatherViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun provideWeekWeatherViewModelFactory(weekWeatherViewModelFactory: WeekWeatherViewModelFactory): ViewModelProvider.Factory
}