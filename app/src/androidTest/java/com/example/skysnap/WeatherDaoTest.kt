package com.example.skysnap

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skysnap.data.database.WeatherDb
import com.example.skysnap.data.database.weather.WeatherDao
import com.example.skysnap.data.database.weather.asDbWeatherObject
import com.example.skysnap.data.database.weather.asDomainWeatherObject
import com.example.skysnap.model.Weather
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {
    private lateinit var weatherDao: WeatherDao
    private lateinit var weatherDb: WeatherDb

    private var weather1 = Weather("Dendermonde", 20.00, 19.00, 18.00, 21.00)
    private var weather2 = Weather("Ghent", 21.00, 20.00, 19.00, 23.00)

    private suspend fun addOneLocationToDb() {
        weatherDao.insert(weather1.asDbWeatherObject())
    }
    private suspend fun addTwoLocationsToDb() {
        weatherDao.insert(weather1.asDbWeatherObject())
        weatherDao.insert(weather2.asDbWeatherObject())
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
        weatherDao = weatherDb.weatherDao()
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
        val allItems = weatherDao.getAll().first()
        assertEquals(allItems[0].asDomainWeatherObject(), weather1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllLocations_returnsAllLocationsFromDB() = runBlocking {
        addTwoLocationsToDb()
        val allItems = weatherDao.getAll().first()
        assertEquals(allItems[0].asDomainWeatherObject(), weather1)
        assertEquals(allItems[1].asDomainWeatherObject(), weather2)
    }
}