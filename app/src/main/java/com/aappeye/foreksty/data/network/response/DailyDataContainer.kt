package com.aappeye.foreksty.data.network.response

import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.google.gson.annotations.SerializedName

data class DailyDataContainer(
    @SerializedName("data")
    val dailyentries: List<DailyWeatherEntry>
)