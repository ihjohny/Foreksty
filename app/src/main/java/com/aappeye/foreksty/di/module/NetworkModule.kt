package com.aappeye.foreksty.di.module

import com.aappeye.foreksty.data.network.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Singleton
    @Binds
    abstract fun bindWeatherNetworkDataService(weatherNetworkDataSourceImpl: WeatherNetworkDataSourceImpl): WeatherNetworkDataSource



    @Singleton
    @Binds
    abstract fun bindConnectivityInterceptor(connectivityInterceptorImpl: ConnectivityInterceptorImpl): ConnectivityInterceptor
}