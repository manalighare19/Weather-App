package com.example.weatherapp.data

import java.io.Serializable
data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
): Serializable
