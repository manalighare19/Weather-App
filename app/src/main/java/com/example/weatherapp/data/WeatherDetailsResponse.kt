package com.example.weatherapp.data

data class WeatherDetailsResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val timezone: Int,
    val id: Int,
    val name: String,
    val main: Main
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


data class Main(
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val feels_like: Double,
    val grnd_level: Int?,
    val humidity: Int?,
    val pressure: Int?,
    val sea_level: Int?,
)
