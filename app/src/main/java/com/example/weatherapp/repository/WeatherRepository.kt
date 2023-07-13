package com.example.weatherapp.repository

import com.example.weatherapp.data.City

interface WeatherRepository {
    suspend fun searchCity(query: String): CitySearchResult
}

sealed class CitySearchResult {
    data class Success(val searchResult: List<City>) : CitySearchResult()
    data class Failure(val message: Exception) : CitySearchResult()
}