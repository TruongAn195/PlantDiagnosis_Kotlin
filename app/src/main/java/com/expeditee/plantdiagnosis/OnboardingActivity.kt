package com.expeditee.plantdiagnosis

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.expeditee.plantdiagnosis.databinding.ActivityOnboardingBinding
import com.expeditee.plantdiagnosis.ui.home.MainActivity
import com.expeditee.plantdiagnosis.ui.onboarding.OnboardingAdapter
import com.expeditee.plantdiagnosis.ui.onboarding.OnboardingItem

class OnboardingActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private lateinit var prefs: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        setupOnboarding()
    }
    
    private fun setupOnboarding() {
        val onboardingItems = listOf(
            OnboardingItem(
                title = "Welcome to Plant Diagnosis",
                description = "Identify plants and diagnose diseases using AI technology",
                iconRes = R.drawable.ic_plant_placeholder
            ),
            OnboardingItem(
                title = "Language Selection",
                description = "Choose your preferred language for the best experience",
                iconRes = R.drawable.ic_settings_nav
            ),
            OnboardingItem(
                title = "Get Started",
                description = "Start identifying plants and keeping them healthy",
                iconRes = R.drawable.ic_home_nav
            )
        )
        
        adapter = OnboardingAdapter(onboardingItems) { position ->
            when (position) {
                0 -> {
                    // First page - just continue
                    binding.viewPager.currentItem = 1
                }
                1 -> {
                    // Language selection page
                    showLanguageSelection()
                }
                2 -> {
                    // Last page - complete onboarding
                    completeOnboarding()
                }
            }
        }
        
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUI(position)
            }
        })
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnSkip.setOnClickListener {
            completeOnboarding()
        }
        
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                completeOnboarding()
            }
        }
    }
    
    private fun updateUI(position: Int) {
        when (position) {
            0 -> {
                binding.btnSkip.visibility = View.VISIBLE
                binding.btnNext.text = "Next"
            }
            1 -> {
                binding.btnSkip.visibility = View.GONE
                binding.btnNext.text = "Select Language"
            }
            2 -> {
                binding.btnSkip.visibility = View.GONE
                binding.btnNext.text = "Get Started"
            }
        }
    }
    
    private fun showLanguageSelection() {
        val languages = arrayOf("English", "Tiếng Việt", "Español", "Français", "Deutsch")
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        builder.setItems(languages) { _, which ->
            val selectedLanguage = languages[which]
            prefs.edit().putString("selected_language", selectedLanguage).apply()
            binding.viewPager.currentItem = 2
        }
        builder.show()
    }
    
    private fun completeOnboarding() {
        prefs.edit().putBoolean("has_completed_onboarding", true).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
