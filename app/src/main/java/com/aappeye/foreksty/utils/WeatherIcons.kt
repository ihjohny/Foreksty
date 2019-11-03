package com.aappeye.foreksty.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.aappeye.foreksty.R

object WeatherIcons {

    fun map(context: Context): Map<String, Drawable> {
        val weatherIconMap = HashMap<String, Drawable>()
        weatherIconMap["clear-day"] = ContextCompat.getDrawable(context, R.drawable.ic_clear_day)!!
        weatherIconMap["clear-night"] = ContextCompat.getDrawable(context, R.drawable.ic_clear_night)!!
        weatherIconMap["rain"] = ContextCompat.getDrawable(context, R.drawable.ic_rain)!!
        weatherIconMap["snow"] = ContextCompat.getDrawable(context, R.drawable.ic_snow)!!
        weatherIconMap["sleet"] = ContextCompat.getDrawable(context, R.drawable.ic_sleet)!!
        weatherIconMap["wind"] = ContextCompat.getDrawable(context, R.drawable.ic_wind)!!
        weatherIconMap["fog"] = ContextCompat.getDrawable(context, R.drawable.ic_fog)!!
        weatherIconMap["cloudy"] = ContextCompat.getDrawable(context, R.drawable.ic_cloudy)!!
        weatherIconMap["partly-cloudy-day"] = ContextCompat.getDrawable(context, R.drawable.ic_partly_cloudy_day)!!
        weatherIconMap["partly-cloudy-night"] = ContextCompat.getDrawable(context, R.drawable.ic_partly_cloudy_night)!!
        return weatherIconMap
    }

}