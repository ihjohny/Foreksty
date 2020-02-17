package com.aappeye.foreksty.data.network


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aappeye.foreksty.data.network.response.WeatherResponse
import com.aappeye.foreksty.internal.NoConnectivityException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

const val EXCEPTION ="Connectivity"

class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiWeatherService: ApiWeatherService
) : WeatherNetworkDataSource {

    private val _downloadedWeather = MutableLiveData<WeatherResponse>()
    override val downloadedWeather: LiveData<WeatherResponse>
        get() = _downloadedWeather

    override suspend fun fetchWeather(location: String, lang: String) {
        try{
            Log.e("Connectivity","Data Fetching Called ")
            val fetchWeather = apiWeatherService
                .getWeather(location, lang)
                .await()
            if(fetchWeather.isSuccessful){
                Log.e(EXCEPTION,"Data Fetch Completed ")
                _downloadedWeather.postValue(fetchWeather.body())
            }
            else{
                Log.e(EXCEPTION,"Data Fetch Failed ")
            }
        }
        catch (e: SocketTimeoutException){
            Log.e(EXCEPTION,"Slow Internet Connection or No Connection Available Now ",e)
        }
        catch (e: NoConnectivityException){
            Log.e(EXCEPTION,"No Internet Connection ",e)
        }
        catch (e: ConnectException){
            Log.e(EXCEPTION, "Connection Interrupted",e)
        }
        catch (e: SSLHandshakeException){
            Log.e(EXCEPTION, "Handshake Failed",e)
        }
    }
}