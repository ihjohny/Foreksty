package com.aappeye.foreksty.data.network

import com.aappeye.foreksty.BuildConfig
import com.aappeye.foreksty.data.network.response.WeatherResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    ): Observable<WeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiWeatherService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeatherService::class.java)
        }
    }
}