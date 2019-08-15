package com.aappeye.foreksty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocation
import java.util.concurrent.locks.Lock

@Database(
    entities = [CurrentWeatherEntry::class, HourlyWeatherEntry::class, DailyWeatherEntry::class, WeatherLocation::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao
    abstract fun hourlyWeatherDao(): HourlyWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object{
        @Volatile private var instance : WeatherDatabase? = null
        private var Lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(Lock){
            instance?: buildDatabase(context).also{ instance = it }
        }
        private fun buildDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "weather_db")
                .build()
    }
}