package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSearchCityBinding

class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
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
            getString(R.string.search_city_fragment_title)
        // Inflate the layout for this fragment
        binding =  FragmentSearchCityBinding.inflate(inflater, container, false)
        return binding.root
    }
}