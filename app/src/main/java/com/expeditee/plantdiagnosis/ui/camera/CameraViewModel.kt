package com.expeditee.plantdiagnosis.ui.camera

import android.net.Uri
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.expeditee.plantdiagnosis.common.IViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * CameraViewModel - ViewModel cho CameraActivity
 * 
 * Quản lý trạng thái camera, plant identification và UI
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class CameraViewModel(application: Application) : IViewModel<CameraViewModel.CameraState>(application) {
    
    companion object {
        private const val TAG = "CameraViewModel"
    }
    
    // UI State
    private val _uiState = MutableStateFlow(CameraState())
    val uiState: StateFlow<CameraState> = _uiState.asStateFlow()
    
    // Camera state
    private val _cameraState = MutableStateFlow(CameraState.CameraStatus.IDLE)
    val cameraState: StateFlow<CameraState.CameraStatus> = _cameraState.asStateFlow()
    
    // Flash state
    private val _isFlashOn = MutableStateFlow(false)
    val isFlashOn: StateFlow<Boolean> = _isFlashOn.asStateFlow()
    
    // Plant identification state
    private val _isIdentifying = MutableStateFlow(false)
    val isIdentifying: StateFlow<Boolean> = _isIdentifying.asStateFlow()
    
    // Progress state
    private val _identificationProgress = MutableStateFlow(0)
    val identificationProgress: StateFlow<Int> = _identificationProgress.asStateFlow()
    
    // Captured image
    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageUri: StateFlow<Uri?> = _capturedImageUri.asStateFlow()
    
    /**
     * Toggle flash state
     */
    fun toggleFlash() {
        _isFlashOn.value = !_isFlashOn.value
        updateState { copy(flashOn = _isFlashOn.value) }
    }
    
    /**
     * Start plant identification process
     */
    fun startPlantIdentification(imageUri: Uri) {
        _capturedImageUri.value = imageUri
        _isIdentifying.value = true
        _identificationProgress.value = 0
        updateState { 
            copy(
                isIdentifying = true,
                progress = 0,
                capturedImageUri = imageUri
            ) 
        }
        
        // Simulate identification process
        simulatePlantIdentification()
    }
    
    /**
     * Simulate plant identification with progress updates
     */
    private fun simulatePlantIdentification() {
        viewModelScope.launch {
            for (progress in 0..100 step 2) {
                _identificationProgress.value = progress
                updateState { copy(progress = progress) }
                delay(50)
            }
            
            // Complete identification
            _isIdentifying.value = false
            updateState { 
                copy(
                    isIdentifying = false,
                    progress = 100,
                    identificationCompleted = true
                ) 
            }
        }
    }
    
    /**
     * Reset identification state
     */
    fun resetIdentification() {
        _isIdentifying.value = false
        _identificationProgress.value = 0
        _capturedImageUri.value = null
        updateState { 
            copy(
                isIdentifying = false,
                progress = 0,
                capturedImageUri = null,
                identificationCompleted = false
            ) 
        }
    }
    
    /**
     * Set camera status
     */
    fun setCameraStatus(status: CameraState.CameraStatus) {
        _cameraState.value = status
        updateState { copy(cameraStatus = status) }
    }
    
    /**
     * Handle camera error
     */
    fun handleCameraError(error: String) {
        updateState { copy(error = error) }
    }
    
    /**
     * Clear error
     */
    fun clearError() {
        updateState { copy(error = null) }
    }
    
    /**
     * Update UI state
     */
    private fun updateState(update: CameraState.() -> CameraState) {
        _uiState.value = _uiState.value.update()
    }
    
    /**
     * Handle state changes from IViewModel
     */
    override fun onState(state: CameraState) {
        // Handle state changes if needed
    }
    
    data class CameraState(
        val isIdentifying: Boolean = false,
        val progress: Int = 0,
        val flashOn: Boolean = false,
        val cameraStatus: CameraStatus = CameraStatus.IDLE,
        val capturedImageUri: Uri? = null,
        val identificationCompleted: Boolean = false,
        val error: String? = null
    ) : IState {
        
        enum class CameraStatus {
            IDLE,
            PREVIEW,
            CAPTURING,
            ERROR
        }
    }
}
