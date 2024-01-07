package com.example.skysnap.data.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for weather data.
 */
@Dao
interface WeatherDao {

    /**
     * Insert a weather item into the database.
     *
     * @param item The weather item to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbWeather)

    /**
     * Update a weather item in the database.
     *
     * @param item The weather item to update.
     */
    @Update
    suspend fun update(item: DbWeather)

    /**
     * Get weather data for a specific location.
     *
     * @param location The location for which to retrieve weather data.
     * @return Flow emitting the weather data for the specified location.
     */
    @Query("SELECT * FROM weather WHERE location = :location")
    fun getWeather(location: String): Flow<DbWeather>

    /**
     * Get all weather data stored in the database.
     *
     * @return Flow emitting a list of all weather data.
     */
    @Query("SELECT * FROM weather")
    fun getAll(): Flow<List<DbWeather>>
}
