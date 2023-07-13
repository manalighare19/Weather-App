package com.example.weatherapp.repository

import com.example.weatherapp.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {
    override suspend fun searchCity(query: String): CitySearchResult =
        withContext(ioDispatcher) {
            try {
                val response = apiService.searchCity(("$query,$US_COUNTRY_CODE"))
                CitySearchResult.Success(response)
            } catch (e: Exception) {
                CitySearchResult.Failure(e)
            }
        }

    override suspend fun getWeatherDetails(lat: Double, long: Double): WeatherDetailsResult =
        withContext(ioDispatcher) {
            try {
                val response = apiService.getWeatherDetails(lat, long)
                WeatherDetailsResult.Success(response)
            } catch (e: Exception) {
                WeatherDetailsResult.Failure(e)
            }
        }

    companion object {
        const val US_COUNTRY_CODE = "US"
    }
}