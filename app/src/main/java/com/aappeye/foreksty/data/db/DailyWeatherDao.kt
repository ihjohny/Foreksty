package com.aappeye.foreksty.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import android.database.sqlite.SQLiteDatabase



@Dao
interface DailyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyWeatherEntries : List<DailyWeatherEntry>)

    @Query("select * from daily_weather")
    fun getDailyWeather(): LiveData<List<DailyWeatherEntry>>

    @Query("select * from daily_weather where time == (select min(time) from daily_weather)")
    fun getTodayWeather(): LiveData<DailyWeatherEntry>

    @Query("delete from daily_weather")
    fun deleteOldEntries()

}