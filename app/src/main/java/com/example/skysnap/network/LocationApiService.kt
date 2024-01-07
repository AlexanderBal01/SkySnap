package com.example.skysnap.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {
    @GET("geo/1.0/direct")
    suspend fun getLocation(@Query("q") location: String, @Query("limit") limit: Int, @Query("appid") apiKey: String) : List<ApiLocation>
}

fun LocationApiService.getLocationAsFlow(location: String): Flow<List<ApiLocation>> = flow {
    try {
        emit(getLocation(location, 1, "546e06657d55ff096daf4bc0302bbf39"))
    } catch (e: Exception) {
        Log.e("API", "getLocationAsFlow: " + e.stackTraceToString())
    }
}