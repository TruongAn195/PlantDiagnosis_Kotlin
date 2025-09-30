package com.expeditee.plantdiagnosis.network

import com.expeditee.plantdiagnosis.data.PlantApiResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.util.concurrent.TimeUnit

interface PlantDiagnosisApi {
    
    @Multipart
    @POST("analyze")
    suspend fun analyzePlant(
        @Part image: MultipartBody.Part
    ): PlantApiResponse
}

class PlantDiagnosisApiService {
    
    private val api: PlantDiagnosisApi
    
    init {
        android.util.Log.d("PlantDiagnosisApiService", "Initializing PlantDiagnosisApiService")
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.plantnet.org/v1/") // PlantNet API base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        api = retrofit.create(PlantDiagnosisApi::class.java)
    }
    
    suspend fun analyzePlant(imageFile: File): PlantApiResponse {
        return try {
            val requestFile = imageFile.asRequestBody("image/*".toMediaType())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            
            api.analyzePlant(imagePart)
        } catch (e: Exception) {
            PlantApiResponse(
                success = false,
                result = null,
                error = e.message ?: "Unknown error occurred"
            )
        }
    }
}
