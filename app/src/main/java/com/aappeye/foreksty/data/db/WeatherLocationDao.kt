package com.aappeye.foreksty.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aappeye.foreksty.data.db.entity.WEATHER_LOCATION_ID
import com.aappeye.foreksty.data.db.entity.WeatherLocation

@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation : WeatherLocation)

    @Query("select * from weather_location where id = ${WEATHER_LOCATION_ID}")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("select * from weather_location where id = ${WEATHER_LOCATION_ID}")
    fun getLocationNonLive(): WeatherLocation?
}