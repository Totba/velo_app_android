package com.example.appli_velo.api

import com.example.appli_velo.model.Parking
import retrofit2.Response
import retrofit2.http.GET

interface ParkingApi {
    @GET("/api/parkings")
    suspend fun getParkings() : Response<List<Parking>>
}