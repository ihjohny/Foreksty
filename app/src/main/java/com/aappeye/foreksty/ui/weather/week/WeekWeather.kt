package com.aappeye.foreksty.ui.weather.week

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aappeye.foreksty.R
import com.aappeye.foreksty.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.week_weather_fragment.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeekWeather : ScopedFragment() {

    @Inject lateinit var viewModelFactory: WeekWeatherViewModelFactory

    private lateinit var viewModel: WeekWeatherViewModel
    private lateinit var weekWeatherAdapter: WeekWeatherAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.week_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeekWeatherViewModel::class.java)
        bindUi()
    }

    private fun bindUi() = launch {
        val dailyWeatherList = viewModel.dailyWeatherList.await()
        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@WeekWeather, Observer {
            if(weatherLocation == null) return@Observer
            // updateLocation(location.latitude, location.longitude)
        })

        dailyWeatherList.observe(this@WeekWeather, Observer {
            if(dailyWeatherList == null) return@Observer
            week_fetch_id.visibility = View.GONE
            week_recyclerView_id.visibility = View.VISIBLE

            weekWeatherAdapter = WeekWeatherAdapter(it.subList(1, it.size), viewModel.isMetricUnit)
            week_recyclerView_id.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = weekWeatherAdapter
            }

        })

    }

}
