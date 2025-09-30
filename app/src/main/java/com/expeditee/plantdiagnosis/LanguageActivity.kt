package com.expeditee.plantdiagnosis

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.expeditee.plantdiagnosis.databinding.ActivityLanguageBinding
import com.expeditee.plantdiagnosis.model.Language

class LanguageActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLanguageBinding
    private lateinit var prefs: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        setupLanguageSelection()
        setupClickListeners()
    }
    
    private fun setupLanguageSelection() {
        val languages = listOf(
            LanguageItem(Language.ENGLISH.displayName, Language.ENGLISH.nativeName, Language.ENGLISH.isoCountry),
            LanguageItem(Language.VIETNAMESE.displayName, Language.VIETNAMESE.nativeName, Language.VIETNAMESE.isoCountry),
            LanguageItem(Language.SPANISH.displayName, Language.SPANISH.nativeName, Language.SPANISH.isoCountry),
            LanguageItem(Language.FRENCH.displayName, Language.FRENCH.nativeName, Language.FRENCH.isoCountry),
            LanguageItem(Language.GERMAN.displayName, Language.GERMAN.nativeName, Language.GERMAN.isoCountry)
        )
        
        val adapter = LanguageAdapter(languages) { selectedLanguage ->
            saveLanguageSelection(selectedLanguage)
            navigateToOnboarding()
        }
        
        binding.recyclerViewLanguages.apply {
            layoutManager = LinearLayoutManager(this@LanguageActivity)
            this.adapter = adapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSkip.setOnClickListener {
            navigateToOnboarding()
        }
    }
    
    private fun saveLanguageSelection(language: LanguageItem) {
        prefs.edit().apply {
            putString("selected_language", language.code)
            putString("selected_language_name", language.name)
            apply()
        }
        
        val selectedLanguage = Language.fromIsoCode(language.code)
        if (selectedLanguage != null) {
            val appConfigSettings = com.expeditee.plantdiagnosis.helper.AppConfigSettings(this)
            appConfigSettings.currentLanguage = selectedLanguage.isoCountry
        }
    }
    
    private fun navigateToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    data class LanguageItem(
        val displayName: String,
        val name: String,
        val code: String
    )
}
