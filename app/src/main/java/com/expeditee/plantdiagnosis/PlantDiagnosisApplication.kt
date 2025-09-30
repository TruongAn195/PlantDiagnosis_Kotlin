package com.expeditee.plantdiagnosis

import android.app.Application
import com.expeditee.plantdiagnosis.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlantDiagnosisApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        android.util.Log.d("PlantDiagnosisApplication", "Application onCreate called")
        
        // AppConfigSettings will be initialized by Koin DI
        
        startKoin {
            androidContext(this@PlantDiagnosisApplication)
            modules(appModule)
        }
        
        android.util.Log.d("PlantDiagnosisApplication", "Koin initialized successfully")
    }
}
