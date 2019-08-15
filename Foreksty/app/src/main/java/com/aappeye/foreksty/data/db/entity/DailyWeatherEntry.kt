package com.aappeye.foreksty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather")
data class DailyWeatherEntry (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val time: Int,
    val summary: String,
    val icon: String,
    val sunriseTime: Int,
    val sunsetTime: Int,
    val precipProbability: Double,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windBearing: Double,
    val uvIndex: Double,
    val visibility: Double
)