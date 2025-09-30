package com.expeditee.plantdiagnosis.network

import com.expeditee.plantdiagnosis.data.PlantApiResponse
import java.io.File

class PlantDiagnosisRepository(
    private val apiService: PlantDiagnosisApiService
) {
    
    init {
        android.util.Log.d("PlantDiagnosisRepository", "Initializing PlantDiagnosisRepository")
    }
    
    suspend fun analyzePlant(imageFile: File): PlantApiResponse {
        return apiService.analyzePlant(imageFile)
    }
    
    // Mock data for testing when API is not available
    fun getMockDiagnosisResult(): PlantApiResponse {
        return PlantApiResponse(
            success = true,
            result = com.expeditee.plantdiagnosis.data.PlantDiagnosisResult(
                plantName = "Rose",
                confidence = 0.85f,
                diseaseDetected = "Black Spot",
                treatmentSuggestion = "Apply fungicide and improve air circulation",
                isHealthy = false
            ),
            error = null
        )
    }
}
