package com.aappeye.foreksty.di.module

import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides

@Module
class LocationModule{

    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return FusedLocationProviderClient(context)
    }
}