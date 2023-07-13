package com.example.weatherapp.data

data class WeatherDetailsResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val timezone: Int,
    val id: Int,
    val name: String
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
