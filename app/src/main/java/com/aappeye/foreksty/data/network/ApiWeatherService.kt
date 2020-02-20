package com.aappeye.foreksty.data.network

import com.aappeye.foreksty.BuildConfig
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.darksky.net/"
const val KEY = BuildConfig.DARK_SKY_API_KEY

interface ApiWeatherService {

    @GET("forecast/"+KEY+"/{location}")
    fun getWeather(
        @Path("location") location: String,
        @Query("lang") lang: String
    ): Deferred<Response<WeatherResponse>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiWeatherService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
/*                .retryOnConnectionFailure(false)*/
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeatherService::class.java)
        }
    }
}