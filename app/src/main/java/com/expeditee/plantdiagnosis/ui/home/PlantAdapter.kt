package com.expeditee.plantdiagnosis.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expeditee.plantdiagnosis.databinding.ItemPlantBinding

class PlantAdapter(
    private val plants: List<PlantItem>,
    private val onPlantClick: (PlantItem) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(private val binding: ItemPlantBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(plant: PlantItem, onPlantClick: (PlantItem) -> Unit) {
            binding.tvPlantName.text = plant.name
            binding.ivPlantImage.setImageResource(plant.imageRes)
            
            binding.root.setOnClickListener {
                onPlantClick(plant)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding = ItemPlantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(plants[position], onPlantClick)
    }

    override fun getItemCount(): Int = plants.size
}
