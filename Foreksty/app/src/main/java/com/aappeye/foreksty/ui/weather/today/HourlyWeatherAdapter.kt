package com.aappeye.foreksty.ui.weather.today

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aappeye.foreksty.R
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.utils.StringFormatter.getDistanceWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getSpeedWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTemperaturesWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTime
import com.aappeye.foreksty.utils.WeatherIcons
import kotlinx.android.synthetic.main.hour_weather_item.view.*

class HourlyWeatherAdapter(
    private val hourlyWeatherList : List<HourlyWeatherEntry>,
    private val isMetricUnit: Boolean
): RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    private lateinit var weatherIcon: Map<String, Drawable>

    class HourlyWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(
            hourlyWeather: HourlyWeatherEntry,
            weatherIconMap: Map<String, Drawable>,
            isMetricUnit: Boolean
        ) {
            with(hourlyWeather) {

                itemView.hwi_tempValue_id.text = getTemperaturesWithUnit(temperature, isMetricUnit)
                itemView.hwi_stateIcon_id.setImageDrawable(weatherIconMap[icon])
                itemView.hwi_windValue_id.text = getSpeedWithUnit(windSpeed,isMetricUnit)
                itemView.hwi_uvValue_id.text = "${uvIndex.toInt()}"
                itemView.hwi_visValue_id.text = getDistanceWithUnit(visibility, isMetricUnit)
                itemView.hwi_time_id.text = getTime(time)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hour_weather_item, parent, false)
        weatherIcon = WeatherIcons.map(parent.context)
        return HourlyWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(hourlyWeatherList[position], weatherIcon, isMetricUnit)
    }

    override fun getItemCount(): Int {
       return hourlyWeatherList.size
    }

}