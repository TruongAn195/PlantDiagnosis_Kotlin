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
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNext()
        }, 2000)
    }
    
    private fun navigateToNext() {
        try {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val hasCompletedOnboarding = prefs.getBoolean("has_completed_onboarding", false)
            val hasSelectedLanguage = prefs.getString("selected_language", null) != null
            
            val intent = when {
                hasCompletedOnboarding -> Intent(this, com.expeditee.plantdiagnosis.ui.home.HomeActivity::class.java)
                hasSelectedLanguage -> Intent(this, OnboardingActivity::class.java)
                else -> Intent(this, LanguageActivity::class.java)
            }
            
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            val intent = Intent(this, LanguageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
