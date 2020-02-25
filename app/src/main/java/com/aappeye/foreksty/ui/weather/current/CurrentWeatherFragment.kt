package com.aappeye.foreksty.ui.weather.current

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.aappeye.foreksty.R
import com.aappeye.foreksty.utils.StringFormatter.getDistanceWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getPercentage
import com.aappeye.foreksty.utils.StringFormatter.getPressureWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getSpeedWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTemperaturesWithUnit
import com.aappeye.foreksty.utils.WeatherIcons
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_current_weather.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

const val TAG = "Fragment"

class CurrentWeatherFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: CurrentWeatherViewModelFactory
    private lateinit var viewModel: CurrentWeatherViewModel
    private var weatherIconMap: Map<String, Drawable>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weatherIconMap = WeatherIcons.map(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        bindUi()

        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    private fun bindUi() {

        viewModel.fetchData()

        viewModel.weatherLocation.observe(viewLifecycleOwner, Observer { weatherLocation ->
            if (weatherLocation == null) return@Observer
            // updateLocation(location.latitude, location.longitude)
        })

        viewModel.weather.observe(viewLifecycleOwner, Observer {

            cwf_fetch_id.visibility = View.GONE
            cwf_content_id.visibility = View.VISIBLE

            if (it == null) return@Observer

            updateTemperatures(it.temperature)
            updateFeelsLike(it.apparentTemperature)
            updateStateText(it.summary)
            updateWind(it.windSpeed)
            updatePrecipition(it.precipProbability)
            updateHumidity(it.humidity)
            updatePressure(it.pressure)
            updateUvIndex(it.uvIndex)
            updateVisibility(it.visibility)
            updateLastUpdate(it.time)
            updateStateIcon(it.icon)

        })
    }

/*    private fun updateLocation(latitude: Double, longitude: Double){
        (activity as AppCompatActivity)?.supportActionBar?.title = "$latitude,$longitude"
    }*/

    private fun updateTemperatures(temperature: Double) {
        cwf_cv0_tempValue_id.text = getTemperaturesWithUnit(temperature, viewModel.isMetricUnit)
    }

    private fun updateFeelsLike(apparentTemperature: Double) {
        val str = "${getString(R.string.feels)} ${getTemperaturesWithUnit(
            apparentTemperature,
            viewModel.isMetricUnit
        )}"
        cwf_cv0_feels_id.text = str
    }

    private fun updateStateText(summary: String) {
        cwf_cv0_state_id.text = summary
    }

    private fun updateWind(windSpeed: Double) {
        cwf_cv1_windValue_id.text = getSpeedWithUnit(windSpeed, viewModel.isMetricUnit)
    }

    private fun updatePrecipition(precipProbability: Double) {
        cwf_cv2_precValue_id.text = getPercentage(precipProbability)
    }

    private fun updateHumidity(humidity: Double) {
        cwf_cv3_humValue_id.text = getPercentage(humidity)
    }

    private fun updatePressure(pressure: Double) {
        cwf_cv4_preValue_id.text = getPressureWithUnit(pressure, viewModel.isMetricUnit)
    }

    private fun updateUvIndex(uvIndex: Double) {
        cwf_cv5_uvValue_id.text = "${uvIndex.toInt()}"
    }

    private fun updateVisibility(visibility: Double) {
        cwf_cv6_visValue_id.text = getDistanceWithUnit(visibility, viewModel.isMetricUnit)
    }

    private fun updateLastUpdate(time: Long) {
        val dt = Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDateTime()
        cwf_cv0_time_id.text =
            dt.format(DateTimeFormatter.ofPattern("hh:mm a dd-MMM-yyyy", Locale.getDefault()))
    }

    private fun updateStateIcon(icon: String) {
        cwf_cv0_stateIcon_id.setImageDrawable(weatherIconMap!![icon])
    }


}
