package com.aappeye.foreksty.data.db

import androidx.room.*
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import io.reactivex.Observable


@Dao
interface DailyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dailyWeatherEntries : List<DailyWeatherEntry>)

    @Query("select * from daily_weather")
    fun getDailyWeather(): Observable<List<DailyWeatherEntry>>

    @Query("select * from daily_weather where time == (select min(time) from daily_weather)")
    fun getTodayWeather(): Observable<DailyWeatherEntry>

    @Query("delete from daily_weather")
    fun deleteOldEntries()

}