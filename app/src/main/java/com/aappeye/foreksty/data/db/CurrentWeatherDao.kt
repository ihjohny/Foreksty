package com.aappeye.foreksty.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aappeye.foreksty.data.db.entity.CURRENT_WEATHER_ID
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import io.reactivex.Observable

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeather() : Observable<CurrentWeatherEntry>
}