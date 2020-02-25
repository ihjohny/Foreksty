package com.aappeye.foreksty.data.network


import android.util.Log
import androidx.lifecycle.LiveData
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.aappeye.foreksty.internal.NoConnectivityException
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiWeatherService: ApiWeatherService
) : WeatherNetworkDataSource {
    
    override fun fetchWeather(location: String, lang: String): Observable<WeatherResponse> {
            Log.d("Network","Data Fetching Called.")
            return apiWeatherService.getWeather(location, lang)
    }
}