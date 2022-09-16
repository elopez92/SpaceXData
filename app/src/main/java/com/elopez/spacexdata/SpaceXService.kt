package com.elopez.spacexdata

import retrofit2.Response
import retrofit2.http.GET

interface SpaceXService {

    @GET("/launches")
    suspend fun getAllLaunches(): Response<LaunchData>
}