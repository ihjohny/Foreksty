package com.aappeye.foreksty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hourly_weather")
data class HourlyWeatherEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val time: Long,
    val icon: String,
    val precipProbability: Double,
    val temperature: Double,
    val apparentTemperature: Double,
    val windSpeed: Double,
    val humidity: Double,
    val pressure: Double,
    val windBearing: Double,
    val uvIndex: Double,
    val visibility: Double
)