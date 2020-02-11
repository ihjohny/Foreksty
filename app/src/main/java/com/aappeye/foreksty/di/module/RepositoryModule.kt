package com.aappeye.foreksty.di.module

import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.data.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

}