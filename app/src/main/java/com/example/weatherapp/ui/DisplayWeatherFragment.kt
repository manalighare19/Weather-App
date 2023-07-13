
package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDisplayWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayWeatherFragment : Fragment() {

    private lateinit var binding: FragmentDisplayWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val cityListAdapter = CityListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
}