package com.expeditee.plantdiagnosis.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantDiagnosisResult(
    val plantName: String,
    val confidence: Float,
    val diseaseDetected: String?,
    val treatmentSuggestion: String?,
    val isHealthy: Boolean
) : Parcelable

@Parcelize
data class PlantApiResponse(
    val success: Boolean,
    val result: PlantDiagnosisResult?,
    val error: String?
) : Parcelable
