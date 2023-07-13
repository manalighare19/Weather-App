package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.City
import com.example.weatherapp.data.WeatherDetailsResponse
import com.example.weatherapp.repository.WeatherDetailsResult
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel  @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    // Get weather details for a searched city
    private val _result = MutableLiveData<UiState>(UiState.Empty)
    val result: LiveData<UiState> = _result

    fun getWeatherDetails(city: City){
        viewModelScope.launch {
            _result.value = UiState.Loading
            when (val response = repository.getWeatherDetails(city.lat, city.lon)) {
                is WeatherDetailsResult.Success -> {
                    if (response.weatherDetails.weather.isEmpty()) {
                        _result.value = UiState.Empty
                    } else {
                        _result.value = UiState.Data(response.weatherDetails)
                    }
                }
                is WeatherDetailsResult.Failure -> {
                    _result.value = UiState.Failure
                }
            }
        }
    }

    sealed class UiState {
        // Page Starting state
        object Init : UiState()

        // Empty State
        object Empty : UiState()

        // Loading Data
        object Loading : UiState()

        // Successfully loaded data
        data class Data(val weatherDetails: WeatherDetailsResponse) : UiState()

        // Failed to load data
        object Failure : UiState()
    }
}