package com.aappeye.foreksty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation (
    val latitude: Double,
    val longitude: Double,
    val timezone: String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID
}