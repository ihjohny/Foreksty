package com.aappeye.foreksty.ui.weather.week

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aappeye.foreksty.R
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.utils.StringFormatter.getDate
import com.aappeye.foreksty.utils.StringFormatter.getDay
import com.aappeye.foreksty.utils.StringFormatter.getDistanceWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getPercentage
import com.aappeye.foreksty.utils.StringFormatter.getPressureWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getSpeedWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTemperaturesWithUnit
import com.aappeye.foreksty.utils.StringFormatter.getTime
import com.aappeye.foreksty.utils.WeatherIcons
import kotlinx.android.synthetic.main.week_item.view.*


class WeekWeatherAdapter(
    private val dailyWeatherList : List<DailyWeatherEntry>,
    private val isMetricUnit: Boolean
): RecyclerView.Adapter<WeekWeatherAdapter.WeekWeatherViewHolder>() {

    private lateinit var weatherIcon: Map<String, Drawable>
    private var expandedList = MutableList(dailyWeatherList.size){false}

    class WeekWeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dailyWeather: DailyWeatherEntry, weatherIconMap: Map<String, Drawable>, isMetricUnit: Boolean, expanded: Boolean){
            with(dailyWeather){

                itemView.wi_day_id.text = getDay(time)
                itemView.wi_date_id.text = getDate(time)
                itemView.wi_minValue_id.text = getTemperaturesWithUnit(temperatureMin, isMetricUnit)
                itemView.wi_maxValue_id.text = getTemperaturesWithUnit(temperatureMax, isMetricUnit)
                itemView.wi_summary_id.text = summary
                itemView.wi_stateIcon_id.setImageDrawable(weatherIconMap[icon])

               itemView.twf_cv1_windValue_id.text = getSpeedWithUnit(windSpeed, isMetricUnit)
                itemView.wi_precValue_id.text = getPercentage(precipProbability)
                itemView.twf_cv1_humValue_id.text = getPercentage(humidity)
                itemView.wi_preValue_id.text = getPressureWithUnit(pressure, isMetricUnit)
                itemView.twf_cv1_uvValue_id.text = "${uvIndex.toInt()}"
                itemView.wi_visValue_id.text = getDistanceWithUnit(visibility, isMetricUnit)
                itemView.wi_sunriseValue_id.text = getTime(sunriseTime)
                itemView.wi_sunsetValue_id.text = getTime(sunsetTime)

                itemView.wi_details_layout_id.setVisibility(if (expanded) View.VISIBLE else View.GONE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.week_item, parent, false)
        weatherIcon = WeatherIcons.map(parent.context)
        return WeekWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {

        holder.bind(dailyWeatherList[position], weatherIcon, isMetricUnit, expandedList[position])

        holder.itemView.setOnClickListener { v ->
            val expanded = expandedList.get(position)
            expandedList[position] = !expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

}