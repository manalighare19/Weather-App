package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.City
import com.example.weatherapp.repository.CitySearchResult
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    // List of all city search results (up to 5)
    private val _result = MutableLiveData<UiState>(UiState.Empty)
    val result: LiveData<UiState> = _result

    // Call to @GET("search") which return required list of cities
    fun searchCity(query: String) {
        viewModelScope.launch {
            _result.value = UiState.Loading
            when (val response = repository.searchCity(query)) {
                is CitySearchResult.Success -> {
                    if (response.searchResult.isEmpty()) {
                        _result.value = UiState.Empty
                    } else {
                        _result.value = UiState.Data(response.searchResult)
                    }
                }
                is CitySearchResult.Failure -> {
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
        data class Data(val searchDetails: List<City>) : UiState()

        // Failed to load data
        object Failure : UiState()
    }

}
