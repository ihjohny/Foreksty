package com.aappeye.foreksty.di.module

import com.aappeye.foreksty.data.repository.ForecastRepository
import com.aappeye.foreksty.data.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

}