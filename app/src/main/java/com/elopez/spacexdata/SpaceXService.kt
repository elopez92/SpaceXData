package com.elopez.spacexdata

import com.elopez.spacexdata.model.LaunchData
import retrofit2.Response
import retrofit2.http.GET

interface SpaceXService {

   /*
   End point returns all launches
    */
    @GET("/v3/launches")
    suspend fun getAllLaunches(): Response<LaunchData>
}