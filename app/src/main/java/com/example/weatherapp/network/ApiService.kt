package com.example.weatherapp.network

import com.example.weatherapp.data.City
import com.example.weatherapp.data.WeatherDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = ""
    ): List<City>

    @GET("data/2.5/weather")
    suspend fun getWeatherDetails(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = ""
    ): WeatherDetailsResponse

}