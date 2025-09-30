package com.expeditee.plantdiagnosis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.expeditee.plantdiagnosis.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupSplash()
    }
    
    private fun setupSplash() {
        // Show splash for 2 seconds then navigate
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNext()
        }, 2000)
    }
    
    private fun navigateToNext() {
        android.util.Log.d("SplashActivity", "navigateToNext called")
        
        // Check if user has completed onboarding
        val hasCompletedOnboarding = getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("has_completed_onboarding", false)
        
        android.util.Log.d("SplashActivity", "hasCompletedOnboarding: $hasCompletedOnboarding")
        
        if (hasCompletedOnboarding) {
            // Go to main activity
            android.util.Log.d("SplashActivity", "Navigating to MainActivity")
            startActivity(Intent(this, com.expeditee.plantdiagnosis.ui.home.MainActivity::class.java))
        } else {
            // Go to onboarding
            android.util.Log.d("SplashActivity", "Navigating to OnboardingActivity")
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }
}
