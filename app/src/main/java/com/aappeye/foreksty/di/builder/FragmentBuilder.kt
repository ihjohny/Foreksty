package com.aappeye.foreksty.di.builder

import com.aappeye.foreksty.ui.weather.current.CurrentWeather
import com.aappeye.foreksty.ui.weather.today.TodayWeather
import com.aappeye.foreksty.ui.weather.week.WeekWeather
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindCurrentWeatherFragment(): CurrentWeather

    @ContributesAndroidInjector
    abstract fun bindTodayWeatherFragment(): TodayWeather

    @ContributesAndroidInjector
    abstract fun bindWeekWeatherFragment(): WeekWeather


}