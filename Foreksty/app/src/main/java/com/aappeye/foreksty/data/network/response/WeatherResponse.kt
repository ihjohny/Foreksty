package com.aappeye.foreksty.data.network.response

import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("currently")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("hourly")
    val hourlyWeatherEntries: HourlyDataContainer,
    @SerializedName("daily")
    val dailyWeatherEntries: DailyDataContainer
)