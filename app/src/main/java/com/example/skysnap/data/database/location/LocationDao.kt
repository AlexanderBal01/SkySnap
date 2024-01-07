package com.example.skysnap.data.database.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for location-related database operations using Room.
 */
@Dao
interface LocationDao {

    /**
     * Inserts a location item into the database. If the item already exists, it will be replaced.
     *
     * @param item The location item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbLocation)

    /**
     * Retrieves all location items from the database as a Flow.
     *
     * @return Flow emitting a list of location items.
     */
    @Query("SELECT * FROM location")
    fun getAllItems(): Flow<List<DbLocation>>

    /**
     * Retrieves a specific location item from the database based on its name.
     *
     * @param name The name of the location.
     * @return Flow emitting the location item.
     */
    @Query("SELECT * from location WHERE name = :name")
    fun getItem(name: String): Flow<DbLocation>
}
