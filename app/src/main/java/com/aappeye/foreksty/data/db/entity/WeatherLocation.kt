package com.aappeye.foreksty.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation (
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val time: Long
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID
    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(time)
            val zoneId = ZoneId.of(timezone)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}