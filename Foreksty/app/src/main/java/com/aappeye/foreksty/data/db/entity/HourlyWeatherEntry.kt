package com.aappeye.foreksty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hourly_weather")
data class HourlyWeatherEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val time: Int,
    val icon: String,
    val precipProbability: Double,
    val temperature: Double,
    val apparentTemperature: Double
)