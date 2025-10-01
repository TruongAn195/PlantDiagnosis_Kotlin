package com.expeditee.plantdiagnosis.ui.home

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentHomeBinding
import com.expeditee.plantdiagnosis.ui.camera.CameraActivity
import org.koin.android.ext.android.inject

class HomeFragment : IFragment<FragmentHomeBinding, CommonViewModel, IViewModel.IState>(
    FragmentHomeBinding::inflate
) {
    
    companion object {
        private const val TAG = "HomeFragment"
    }
    
    private lateinit var plantAdapter: PlantAdapter
    
    override fun getLazyViewModel() = lazy { 
        inject<CommonViewModel>().value 
    }
    
    override fun initViews() {
        setupClickListeners()
        setupPlantsRecyclerView()
    }
    
    private fun setupClickListeners() {
        // Core Features
        viewBinding.cardPlantIdentify.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            startActivity(intent)
        }
        
        viewBinding.cardMyPlants.setOnClickListener {
            // Navigate to my plants
        }
        
        viewBinding.cardPlantHealth.setOnClickListener {
            // Navigate to plant health
        }
        
        viewBinding.cardReminders.setOnClickListener {
            // Navigate to reminders
        }
        
        // Search bar
        viewBinding.cardSearch.setOnClickListener {
            // Open search/camera
        }
        
        // Specialist consultation
        viewBinding.btnAskExpert.setOnClickListener {
            // Navigate to expert consultation
        }
        
        // View all buttons
        viewBinding.tvViewAll.setOnClickListener {
            // Show all features
        }
        
        viewBinding.tvViewAllPlants.setOnClickListener {
            // Show all plants
        }
    }
    
    private fun setupPlantsRecyclerView() {
        val plants = listOf(
            PlantItem("Fiddle Leaf Fig", R.drawable.ic_plant),
            PlantItem("Snake Plant", R.drawable.ic_plant),
            PlantItem("Monstera", R.drawable.ic_plant)
        )
        
        plantAdapter = PlantAdapter(plants) { plant ->
            // Handle plant click
        }
        
        viewBinding.rvPlants.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = plantAdapter
        }
    }
}