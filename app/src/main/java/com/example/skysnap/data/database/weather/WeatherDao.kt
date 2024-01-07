package com.example.skysnap.data.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbWeather)

    @Update
    suspend fun update(item: DbWeather)

    @Query("SELECT * FROM weather WHERE location = :location")
    fun getWeather(location: String): Flow<DbWeather>

    @Query("SELECT * FROM weather")
    fun getAll(): Flow<List<DbWeather>>
}