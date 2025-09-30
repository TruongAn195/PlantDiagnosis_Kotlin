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
        
        try {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            
            // Check if user has completed onboarding
            val hasCompletedOnboarding = prefs.getBoolean("has_completed_onboarding", false)
            val hasSelectedLanguage = prefs.getString("selected_language", null) != null
            
            android.util.Log.d("SplashActivity", "hasCompletedOnboarding: $hasCompletedOnboarding")
            android.util.Log.d("SplashActivity", "hasSelectedLanguage: $hasSelectedLanguage")
            
            when {
                hasCompletedOnboarding -> {
                    // User has completed everything, go to HomeActivity
                    android.util.Log.d("SplashActivity", "Navigating to HomeActivity")
                    val intent = Intent(this, com.expeditee.plantdiagnosis.ui.home.HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                hasSelectedLanguage -> {
                    // User has selected language but not completed onboarding
                    android.util.Log.d("SplashActivity", "Navigating to OnboardingActivity")
                    val intent = Intent(this, OnboardingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else -> {
                    // User hasn't selected language yet
                    android.util.Log.d("SplashActivity", "Navigating to LanguageActivity")
                    val intent = Intent(this, LanguageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            finish()
        } catch (e: Exception) {
            android.util.Log.e("SplashActivity", "Error in navigateToNext: ${e.message}", e)
            // Fallback to LanguageActivity if there's an error
            try {
                val intent = Intent(this, LanguageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (fallbackError: Exception) {
                android.util.Log.e("SplashActivity", "Fallback also failed: ${fallbackError.message}", fallbackError)
            }
        }
    }
}
