package com.aappeye.foreksty.di.component

import android.app.Application
import com.aappeye.foreksty.ForekstyApplication
import com.aappeye.foreksty.di.builder.ActivityBuilder
import com.aappeye.foreksty.di.builder.FragmentBuilder
import com.aappeye.foreksty.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf (AndroidInjectionModule::class, ActivityBuilder::class, FragmentBuilder::class ,AppModule::class, DatabaseModule::class,
    NetworkModule::class, RepositoryModule::class, LocationModule::class,ViewModelModule::class))
interface AppComponent : AndroidInjector<ForekstyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}