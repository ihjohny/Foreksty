package com.aappeye.foreksty.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry

@Dao
interface HourlyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hourlyWeatherEntries : List<HourlyWeatherEntry>)

    @Query("select * from hourly_weather")
    fun getHourlyWeather(): LiveData<List<HourlyWeatherEntry>>

    @Query("delete from hourly_weather")
    fun deleteOldEntries()
}