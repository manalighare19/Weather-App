package com.example.weatherapp.network

import com.example.weatherapp.data.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = ""
    ): List<City>
}