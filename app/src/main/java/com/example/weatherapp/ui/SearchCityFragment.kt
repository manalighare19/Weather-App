package com.example.weatherapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.City
import com.example.weatherapp.databinding.FragmentSearchCityBinding
import com.example.weatherapp.ui.viewmodels.SearchCityViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCityFragment : Fragment(), RowClickListener {

    private lateinit var binding: FragmentSearchCityBinding
    private val viewModel: SearchCityViewModel by viewModels()
    private val cityListAdapter = CityListAdapter(this@SearchCityFragment)
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
            getString(R.string.search_city_fragment_title)
        // Inflate the layout for this fragment
        binding =  FragmentSearchCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.citiesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cityListAdapter
        }

        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Handle search query submission
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Handle search query text changes
                    viewModel.searchCity(newText ?: "")
                    return true
                }
            })

            viewModel.result.observe(viewLifecycleOwner) { state ->
                binding.apply {
                    when (state) {
                        is SearchCityViewModel.UiState.Data -> {
                            progressBar.visibility = View.GONE
                            cityListAdapter.submitList(state.searchDetails)
                        }
                        SearchCityViewModel.UiState.Empty -> {
                            progressBar.visibility = View.GONE
                            cityListAdapter.submitList(emptyList())
                        }
                        SearchCityViewModel.UiState.Failure -> {
                            progressBar.visibility = View.GONE
                        }
                        SearchCityViewModel.UiState.Init,
                        SearchCityViewModel.UiState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onCityClicked(city: City) {
        sharedPreferences.edit().putString("city", Gson().toJson(city)).apply()
        findNavController().navigate(
            SearchCityFragmentDirections.actionSearchCityToDisplayWeather(city)
        )
    }
}

interface RowClickListener {
    fun onCityClicked(city: City)
}