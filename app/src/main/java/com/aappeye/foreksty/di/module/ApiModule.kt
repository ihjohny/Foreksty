package com.aappeye.foreksty.di.module

import com.aappeye.foreksty.data.network.ApiWeatherService
import com.aappeye.foreksty.data.network.ConnectivityInterceptorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule{

    @Singleton
    @Provides
    fun bindApiWeatherService(connectivityInterceptor: ConnectivityInterceptorImpl): ApiWeatherService{
        return ApiWeatherService.invoke(connectivityInterceptor)
    }
}