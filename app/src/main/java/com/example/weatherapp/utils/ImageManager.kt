package com.example.weatherapp.utils

object ImageManager {
    private const val IMAGE_URL = "https://openweathermap.org/img/wn/"
    private const val IMAGE_SIZE = "@2x"
    private const val IMAGE_FORMAT = ".png"

    fun getImageUrl(icon: String): String {
        return IMAGE_URL.plus(icon).plus(IMAGE_SIZE).plus(IMAGE_FORMAT)
    }
}