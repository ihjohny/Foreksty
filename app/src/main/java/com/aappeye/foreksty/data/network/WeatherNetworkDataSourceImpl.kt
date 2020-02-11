package com.aappeye.foreksty.data.network


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.aappeye.foreksty.internal.NoConnectivityException
import java.net.SocketTimeoutException
import javax.inject.Inject


class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiWeatherService: ApiWeatherService
) : WeatherNetworkDataSource {

    private val _downloadedWeather = MutableLiveData<WeatherResponse>()
    override val downloadedWeather: LiveData<WeatherResponse>
        get() = _downloadedWeather

    override suspend fun fetchWeather(location: String, lang: String) {
        try{
            val fetchWeather = apiWeatherService
                .getWeather(location, lang)
                .await()
            if(fetchWeather.isSuccessful){
                _downloadedWeather.postValue(fetchWeather.body())
            }
            else{
                Log.e("Connectivity","Data fetch failed ")
            }
        }
        catch (e: SocketTimeoutException){
            Log.e("Connectivity","Slow Internet Connection or No Connection Available Now",e)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection ",e)
        }
    }
}