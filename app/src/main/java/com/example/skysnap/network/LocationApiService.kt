package com.example.skysnap.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for interacting with the location API.
 */
interface LocationApiService {
    /**
     * Fetches location data from the API based on the provided query parameters.
     *
     * @param location The location query.
     * @param limit The limit of results to retrieve.
     * @param apiKey The API key for authentication.
     * @return List of [ApiLocation] objects representing the location data.
     */
    @GET("geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") location: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<ApiLocation>
}

/**
 * Extension function to fetch location data from the API as a Flow.
 *
 * @param location The location query.
 * @return Flow emitting a list of [ApiLocation] objects.
 */
fun LocationApiService.getLocationAsFlow(location: String): Flow<List<ApiLocation>> = flow {
    try {
        // Emit the result of fetching location data from the API.
        emit(getLocation(location, 1, "546e06657d55ff096daf4bc0302bbf39"))
    } catch (e: Exception) {
        // Log any exceptions that occur during the API call.
        Log.e("API", "getLocationAsFlow: " + e.stackTraceToString())
    }
}
