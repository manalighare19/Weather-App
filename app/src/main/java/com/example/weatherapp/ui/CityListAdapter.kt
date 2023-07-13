package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.City
import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.databinding.ItemCityBinding

class CityListAdapter(private val rowClickListener: RowClickListener) :
    ListAdapter<City, CityListAdapter.CityItemViewHolder>(DiffCallback()) {

    class CityItemViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: City) {
            binding.name.text = "${data.name}, " + "${data.state}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        return CityItemViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            rowClickListener.onCityClicked(getItem(position))
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }
}