package com.aappeye.foreksty.data.network.response

import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.google.gson.annotations.SerializedName

data class HourlyDataContainer (
    @SerializedName("data")
    val hourlyentries: List<HourlyWeatherEntry>
)