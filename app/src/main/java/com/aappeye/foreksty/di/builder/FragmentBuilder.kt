package com.aappeye.foreksty.di.builder

import com.aappeye.foreksty.ui.weather.current.CurrentWeatherFragment
import com.aappeye.foreksty.ui.weather.today.TodayWeatherFragment
import com.aappeye.foreksty.ui.weather.week.WeekWeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindCurrentWeatherFragment(): CurrentWeatherFragment

    @ContributesAndroidInjector
    abstract fun bindTodayWeatherFragment(): TodayWeatherFragment

    @ContributesAndroidInjector
    abstract fun bindWeekWeatherFragment(): WeekWeatherFragment


}