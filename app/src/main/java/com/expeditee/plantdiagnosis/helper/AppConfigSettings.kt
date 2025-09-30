package com.expeditee.plantdiagnosis.helper

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.module.Module
import org.koin.dsl.module

class AppConfigSettings(private val context: Context) {
    
    init {
        android.util.Log.d("AppConfigSettings", "AppConfigSettings initialized")
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences("plant_diagnosis_prefs", Context.MODE_PRIVATE)
    
    var introCompleted: Boolean
        get() = prefs.getBoolean("intro_completed", false)
        set(value) = prefs.edit().putBoolean("intro_completed", value).apply()
    
    var currentLanguage: String?
        get() = prefs.getString("current_language", "en")
        set(value) = prefs.edit().putString("current_language", value).apply()
    
    var isFirstLaunch: Boolean
        get() = prefs.getBoolean("is_first_launch", true)
        set(value) = prefs.edit().putBoolean("is_first_launch", value).apply()
    
    var hasCompletedOnboarding: Boolean
        get() = prefs.getBoolean("has_completed_onboarding", false)
        set(value) = prefs.edit().putBoolean("has_completed_onboarding", value).apply()
    
    var selectedLanguage: String?
        get() = prefs.getString("selected_language", "English")
        set(value) = prefs.edit().putString("selected_language", value).apply()
}

val appConfigModule: Module = module {
    single { AppConfigSettings(get()) }
}
