package com.example.weatherapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentDisplayWeatherBinding
import com.example.weatherapp.ui.viewmodels.WeatherDetailsViewModel
import com.example.weatherapp.utils.ImageManager.getImageUrl
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
        binding = FragmentDisplayWeatherBinding.inflate(inflater, container, false)
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
            binding.apply {
                when (state) {
                    is WeatherDetailsViewModel.UiState.Data -> {
                        progressBar.visibility = View.GONE
                        binding.cityName.text =
                            getString(R.string.city_format, cityDetails?.name, cityDetails?.state)
                        binding.cityTemp.text = getString(
                            R.string.temp_format,
                            state.weatherDetails.main.temp.toInt().toString()
                        )
                        binding.cityTempDesc.text = state.weatherDetails.weather[0].description
                        Glide.with(requireContext())
                            .load(getImageUrl(state.weatherDetails.weather[0].icon))
                            .into(binding.cityTempImage)
                    }
                    WeatherDetailsViewModel.UiState.Empty -> {
                        progressBar.visibility = View.GONE
                        emptyStateView.apply {
                            visibility = View.VISIBLE
                            emptyText.text = getString(R.string.empty_text)
                            emptyImage.setImageResource(R.drawable.ic_search)
                        }
                    }
                    WeatherDetailsViewModel.UiState.Failure -> {
                        progressBar.visibility = View.GONE
                        emptyStateView.apply {
                            visibility = View.VISIBLE
                            emptyText.text = getString(R.string.error_text)
                            emptyImage.setImageResource(R.drawable.ic_error)
                        }
                    }
                    WeatherDetailsViewModel.UiState.Init,
                    WeatherDetailsViewModel.UiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        emptyStateView.visibility = View.GONE

                    }
                }
            }
        }
    }
}