package com.aappeye.foreksty.ui.weather.today

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.aappeye.foreksty.R
import com.aappeye.foreksty.ui.base.ScopedFragment
import com.aappeye.foreksty.ui.weather.current.CurrentWeatherViewModelFactory
import com.aappeye.foreksty.utils.WeatherIcons
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class TodayWeather : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: TodayWeatherViewModelFactory by instance()
    private lateinit var viewModel: TodayWeatherViewModel
    private var weatherIconMap: Map<String, Drawable>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherIconMap = context?.let { WeatherIcons.map(it) }
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TodayWeatherViewModel::class.java)
        bindUi()
    }

    private fun bindUi() {

    }

}
