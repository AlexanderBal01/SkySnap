package com.example.skysnap.data.database.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbLocation)

    @Query("SELECT * FROM location ORDER BY name ASC")
    fun getAllItems(): Flow<List<DbLocation>>

    @Query("SELECT * from location WHERE name = :name")
    fun getItem(name: String): Flow<DbLocation>
}