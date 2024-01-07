package com.example.skysnap.data.database.weather

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    suspend fun delete(item: DbWeather)

    @Query("SELECT * FROM weather ORDER BY cityName ASC")
    fun getAllItems(): Flow<List<DbWeather>>

    @Query("SELECT * FROM weather WHERE cityName = :name")
    fun getItem(name: String): Flow<DbWeather>
}