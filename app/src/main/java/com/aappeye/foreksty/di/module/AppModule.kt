package com.aappeye.foreksty.di.module

import android.app.Application
import android.content.Context
import com.aappeye.foreksty.data.provider.LocationProvider
import com.aappeye.foreksty.data.provider.LocationProviderImpl
import com.aappeye.foreksty.data.provider.SettingsProvider
import com.aappeye.foreksty.data.provider.SettingsProviderImpl
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Binds
    abstract fun provideApplicationContext(application: Application?): Context

    @Singleton
    @Binds
    abstract fun provideSettingsProvider(settingsProviderImp: SettingsProviderImpl): SettingsProvider

    @Singleton
    @Binds
    abstract fun provideLocationProvider(locationProviderImpl: LocationProviderImpl): LocationProvider

}
