package com.aappeye.foreksty.ui.weather.today

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aappeye.foreksty.R
import com.aappeye.foreksty.ui.base.ScopedFragment
import com.aappeye.foreksty.utils.StringFormatter.getDate
import com.aappeye.foreksty.utils.StringFormatter.getDay
import com.aappeye.foreksty.utils.StringFormatter.getDistanceWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getPercentage
import com.aappeye.foreksty.utils.StringFormatter.getPressureWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getSpeedWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTemperaturesWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTime
import com.aappeye.foreksty.utils.WeatherIcons
import kotlinx.android.synthetic.main.today_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class TodayWeather : ScopedFragment(), KodeinAware {

    override val kodein by kodein()
    private val viewModelFactory: TodayWeatherViewModelFactory by instance()
    private lateinit var viewModel: TodayWeatherViewModel
    private var weatherIconMap: Map<String, Drawable>? = null
    private lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

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

    private fun bindUi() = launch{
        val todayWeather = viewModel.todayWeather.await()
        val hourlyWeatherList  = viewModel.hourlyWeatherList.await()
        val weatherLocation = viewModel.weatherLocation.await()

        todayWeather.observe(this@TodayWeather, Observer {

            if( it == null) return@Observer
            twf_fetch_id.visibility = View.GONE
            twf_content_id.visibility = View.VISIBLE

            updateDay(it.time)
            updateDate(it.time)
            updateStateIcon(it.icon)
            updateMinTemp(it.temperatureMin)
            updateMaxTemp(it.temperatureMax)
            updateSummary(it.summary)

            updateWind(it.windSpeed)
            updatePrecipition(it.precipProbability)
            updateHumidity(it.humidity)
            updatePressure(it.pressure)
            updateUvIndex(it.uvIndex)
            updateVisibility(it.visibility)
            updateSunrise(it.sunriseTime)
            updateSunset(it.sunsetTime)

        })

        hourlyWeatherList.observe(this@TodayWeather, Observer {
            if( it == null) return@Observer
            twf_fetch_id.visibility = View.GONE
            twf_content_id.visibility = View.VISIBLE

            hourlyWeatherAdapter = HourlyWeatherAdapter(it, viewModel.isMetricUnit)
            twf_cv1_hourly_recyclerView_id.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                adapter = hourlyWeatherAdapter
            }

        })
    }

    private fun updateDay(time: Long) {
        twf_cv0_day_id.text = getDay(time)
    }

    private fun updateDate(time: Long) {
        twf_cv0_date_id.text = getDate(time)
    }

    private fun updateStateIcon(icon: String) {
        twf_cv0_stateIcon_id.setImageDrawable(weatherIconMap!![icon])
    }

    private fun updateMinTemp(temperatureMin: Double) {
        twf_cv0_minValue_id.text = getTemperaturesWithUnit(temperatureMin, viewModel.isMetricUnit)
    }

    private fun updateMaxTemp(temperatureMax: Double) {
        twf_cv0_maxValue_id.text = getTemperaturesWithUnit(temperatureMax, viewModel.isMetricUnit)
    }

    private fun updateSummary(summary: String) {
        twf_cv0_summary_id.text = summary
    }

    private fun updateWind(windSpeed: Double) {
        twf_cv1_windValue_id.text = getSpeedWithUnit(windSpeed, viewModel.isMetricUnit)
    }

    private fun updatePrecipition(precipProbability: Double) {
        twf_cv1_precValue_id.text = getPercentage(precipProbability)
    }

    private fun updateHumidity(humidity: Double) {
        twf_cv1_humValue_id.text = getPercentage(humidity)
    }

    private fun updatePressure(pressure: Double) {
        twf_cv1_preValue_id.text = getPressureWithUnit(pressure, viewModel.isMetricUnit)
    }

    private fun updateUvIndex(uvIndex: Double) {
        twf_cv1_uvValue_id.text = "${uvIndex.toInt()}"
    }

    private fun updateVisibility(visibility: Double) {
        twf_cv1_visValue_id.text = getDistanceWithUnit(visibility, viewModel.isMetricUnit)
    }

    private fun updateSunrise(sunriseTime: Long) {
        twf_cv1_sunriseValue_id.text = getTime(sunriseTime)
    }

    private fun updateSunset(sunsetTime: Long) {
        twf_cv1_sunsetValue_id.text = getTime(sunsetTime)
    }

}
