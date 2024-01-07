package com.example.skysnap.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skysnap.data.database.location.DbLocation
import com.example.skysnap.data.database.location.LocationDao
import com.example.skysnap.data.database.weather.DbWeather
import com.example.skysnap.data.database.weather.WeatherDao

@Database(entities = [DbWeather::class, DbLocation::class], version = 3, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var Instance: WeatherDb? = null

        fun getDatabase(context: Context): WeatherDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WeatherDb::class.java, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}