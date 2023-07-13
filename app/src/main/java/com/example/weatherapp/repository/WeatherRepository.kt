package com.example.weatherapp.repository

import com.example.weatherapp.data.City
import com.example.weatherapp.data.WeatherDetailsResponse

interface WeatherRepository {
    suspend fun searchCity(query: String): CitySearchResult
    suspend fun getWeatherDetails(lat: Double, long: Double): WeatherDetailsResult
}

sealed class CitySearchResult {
    data class Success(val searchResult: List<City>) : CitySearchResult()
    data class Failure(val message: Exception) : CitySearchResult()
}

sealed class WeatherDetailsResult {
    data class Success(val weatherDetails: WeatherDetailsResponse) : WeatherDetailsResult()
    data class Failure(val message: Exception) : WeatherDetailsResult()
}