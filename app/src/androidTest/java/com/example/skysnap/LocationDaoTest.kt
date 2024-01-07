package com.example.skysnap

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skysnap.data.database.WeatherDb
import com.example.skysnap.data.database.location.LocationDao
import com.example.skysnap.data.database.location.asDbLocationObject
import com.example.skysnap.data.database.location.asDomainLocationObject
import com.example.skysnap.model.Location
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {
    private lateinit var locationDao: LocationDao
    private lateinit var weatherDb: WeatherDb

    private var location1 = Location("Dendermonde", 51.0312293, 4.098112)
    private var location2 = Location("Ghent", 51.0538286, 3.7250121)

    private suspend fun addOneLocationToDb() {
        locationDao.insert(location1.asDbLocationObject())
    }
    private suspend fun addTwoLocationsToDb() {
        locationDao.insert(location1.asDbLocationObject())
        locationDao.insert(location2.asDbLocationObject())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        weatherDb = Room.inMemoryDatabaseBuilder(context, WeatherDb::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        locationDao = weatherDb.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        weatherDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertLocationIntoDB() = runBlocking {
        addOneLocationToDb()
        val allItems = locationDao.getAllItems().first()
        assertEquals(allItems[0].asDomainLocationObject(), location1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllLocations_returnsAllLocationsFromDB() = runBlocking {
        addTwoLocationsToDb()
        val allItems = locationDao.getAllItems().first()
        assertEquals(allItems[0].asDomainLocationObject(), location1)
        assertEquals(allItems[1].asDomainLocationObject(), location2)
    }
}