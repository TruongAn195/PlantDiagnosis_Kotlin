package com.expeditee.plantdiagnosis.di

import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.helper.AppConfigSettings
import com.expeditee.plantdiagnosis.network.PlantDiagnosisApiService
import com.expeditee.plantdiagnosis.network.PlantDiagnosisRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    
    // Helper
    single { 
        android.util.Log.d("appModule", "Creating AppConfigSettings")
        AppConfigSettings(get()) 
    }
    
    // Network
    single { 
        android.util.Log.d("appModule", "Creating PlantDiagnosisApiService")
        PlantDiagnosisApiService() 
    }
    
    // Repository
    single { 
        android.util.Log.d("appModule", "Creating PlantDiagnosisRepository")
        PlantDiagnosisRepository(get()) 
    }
    
    // ViewModels
    viewModel { 
        android.util.Log.d("appModule", "Creating CommonViewModel")
        CommonViewModel(get()) 
    }
}
