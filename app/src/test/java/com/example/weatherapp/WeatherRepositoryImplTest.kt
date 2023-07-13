package com.example.weatherapp

import com.example.weatherapp.data.*
import com.example.weatherapp.network.ApiService
import com.example.weatherapp.repository.CitySearchResult
import com.example.weatherapp.repository.WeatherDetailsResult
import com.example.weatherapp.repository.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var mockApiService: ApiService
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        mockApiService = mockk()
        repository = WeatherRepositoryImpl(mockApiService, testDispatcher)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testSearchCitySuccess() = runTest {
        // Mock API response
        val query = "london"
        val response = listOf(
            City(
                name = "London",
                lat = 37.1289771,
                lon = -84.0832646,
                country = "US",
                state = "Kentucky"
            ),
            City(
                name = "London",
                lat = 37.1289771,
                lon = -84.0832646,
                country = "US",
                state = "Kentucky"
            )
        )
        coEvery { mockApiService.searchCity("$query,${WeatherRepositoryImpl.US_COUNTRY_CODE}") }
            .returns(response)


        // Verify the result
        assertEquals(repository.searchCity(query), CitySearchResult.Success(response) )
    }


    @Test
    fun testSearchCityFailure() = runTest {
        // Mock API exception
        val query = "Invalid Query"
        val exception = Exception("API Error")
        coEvery { mockApiService.searchCity("$query,${WeatherRepositoryImpl.US_COUNTRY_CODE}") }
            .throws(exception)

        // Verify the result
        assertEquals(repository.searchCity(query), CitySearchResult.Failure(exception))
    }

    @Test
    fun testWeatherDetailsSuccess() = runTest {
        // Mock API response
        val lat = 40.7128
        val long = -74.0060
        val response = WeatherDetailsResponse(
            Coord(123.456, 78.90),
            listOf(
                Weather(800, "Clear", "clear sky", "01d"),
                Weather(500, "Rain", "light rain", "10d")
            ),
            "stations",
            7200,
            2643743,
            "London",
            Main(22.5, 25.0, 20.0, 23.0, 1015, 70, 1012, 1015)
        )
        coEvery { mockApiService.getWeatherDetails(lat, long) }
            .returns(response)

        // Call the method under test
        val result = repository.getWeatherDetails(lat, long)

        // Verify the result
        assertEquals(result, WeatherDetailsResult.Success(response))
    }

    @Test
    fun testWeatherDetailsFailure() = runTest {
        // Mock API exception
        val lat = 40.7128
        val long = -74.0060
        val exception = Exception("API Error")
        coEvery { mockApiService.getWeatherDetails(lat, long) }
            .throws(exception)

        // Call the method under test
        val result = repository.getWeatherDetails(lat, long)

        // Verify the result
        assertEquals(result, WeatherDetailsResult.Failure(exception))

    }

}