package com.example.skysnap.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.skysnap.data.database.location.DbLocation
import com.example.skysnap.data.database.location.LocationDao
import com.example.skysnap.data.database.weather.DbWeather
import com.example.skysnap.data.database.weather.WeatherDao

/**
 * Room database for storing weather and location data.
 */
@Database(entities = [DbWeather::class, DbLocation::class], version = 3, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    // Abstract functions to obtain DAOs (Data Access Objects)
    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao

    companion object {
        // Volatile ensures that the variable is visible to all threads
        @Volatile
        private var Instance: WeatherDb? = null

        /**
         * Get an instance of the WeatherDb database.
         *
         * @param context Application context.
         * @return Instance of WeatherDb.
         */
        fun getDatabase(context: Context): WeatherDb {
            return Instance ?: synchronized(this) {
                // Create the database instance if it doesn't exist
                Room.databaseBuilder(context, WeatherDb::class.java, "weather_database")
                    .fallbackToDestructiveMigration() // Handles database migrations by recreating the database if needed
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
