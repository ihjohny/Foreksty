package com.aappeye.foreksty.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry

@Dao
interface DailyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyWeatherEntries : List<DailyWeatherEntry>)

    @Query("select * from daily_weather")
    fun getDailyWeather(): LiveData<List<DailyWeatherEntry>>

    @Query("delete from daily_weather")
    fun deleteOldEntries()
}