
package com.example.weatherapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentDisplayWeatherBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayWeatherFragment : Fragment() {

    private lateinit var binding: FragmentDisplayWeatherBinding
    private val viewModel: WeatherDetailsViewModel by viewModels()
    private var cityDetails: City? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.display_weather_fragment_title)
        // Inflate the layout for this fragment
        binding =  FragmentDisplayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchCityButton.setOnClickListener {
            findNavController().navigate(DisplayWeatherFragmentDirections.actionDisplayWeatherToSearchCity())
        }

        val city = sharedPreferences.getString("city", null)

        cityDetails = city?.let { Gson().fromJson(city, City::class.java) }

        cityDetails?.let { viewModel.getWeatherDetails(it) }

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is WeatherDetailsViewModel.UiState.Data -> {
                    binding.cityName.text = state.weatherDetails.name
                }
                WeatherDetailsViewModel.UiState.Empty -> {
                    Log.d("Display Weather Fragment", "Empty")
                }
                WeatherDetailsViewModel.UiState.Failure -> {
                    Log.d("Display Weather Fragment", "Failure")
                }
                WeatherDetailsViewModel.UiState.Init,
                WeatherDetailsViewModel.UiState.Loading -> {
                    Log.d("Display Weather Fragment", "Loading")
                }
            }
        }
    }
}