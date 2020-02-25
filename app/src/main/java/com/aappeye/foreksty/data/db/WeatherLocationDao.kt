package com.aappeye.foreksty.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aappeye.foreksty.data.db.entity.WEATHER_LOCATION_ID
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import io.reactivex.Single

@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation : WeatherLocationEntry)

    @Query("select * from weather_location where id = ${WEATHER_LOCATION_ID}")
    fun getLocation(): Single<WeatherLocationEntry>
}