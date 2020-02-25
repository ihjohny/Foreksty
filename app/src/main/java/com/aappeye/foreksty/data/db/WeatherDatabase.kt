package com.aappeye.foreksty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aappeye.foreksty.data.db.entity.CurrentWeatherEntry
import com.aappeye.foreksty.data.db.entity.DailyWeatherEntry
import com.aappeye.foreksty.data.db.entity.HourlyWeatherEntry
import com.aappeye.foreksty.data.db.entity.WeatherLocationEntry
import com.aappeye.foreksty.utils.LocalDateConverter

@Database(
    entities = [CurrentWeatherEntry::class, HourlyWeatherEntry::class, DailyWeatherEntry::class, WeatherLocationEntry::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
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