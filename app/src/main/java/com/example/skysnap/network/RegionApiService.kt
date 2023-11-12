package com.example.skysnap.network

import androidx.compose.ui.res.stringResource
import com.example.skysnap.R
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionApiService {
    @GET("/locations/v1/regions?apikey=FxZbnUgAphCVmuwkP9oPpL5pR4mySqFN&language=nl-be")
    suspend fun getRegions(): List<ApiRegion>
}