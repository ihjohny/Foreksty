package com.aappeye.foreksty
import android.app.Application
import androidx.preference.PreferenceManager
import com.aappeye.foreksty.di.component.AppComponent
import com.aappeye.foreksty.di.component.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class ForekstyApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preference,false)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}
