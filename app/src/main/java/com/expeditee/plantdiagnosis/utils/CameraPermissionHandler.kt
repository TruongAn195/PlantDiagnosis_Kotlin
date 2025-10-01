package com.expeditee.plantdiagnosis.utils

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.expeditee.plantdiagnosis.R

/**
 * Handler để xử lý camera permission với logic phức tạp
 */
class CameraPermissionHandler(
    private val activity: FragmentActivity,
    private val permissionLauncher: ActivityResultLauncher<String>
) {
    
    companion object {
        private const val TAG = "CameraPermissionHandler"
    }
    
    /**
     * Kiểm tra và xử lý camera permission trước khi mở camera
     */
    fun checkAndRequestCameraPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        Log.d(TAG, "Checking camera permission...")
        
        when {
            PermissionUtils.hasCameraPermission(activity) -> {
                Log.d(TAG, "Camera permission already granted")
                onPermissionGranted()
            }
            
            PermissionUtils.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                Log.d(TAG, "Showing permission rationale")
                showPermissionRationale(onPermissionGranted, onPermissionDenied)
            }
            
            else -> {
                Log.d(TAG, "Requesting camera permission")
                requestCameraPermission()
            }
        }
    }
    
    /**
     * Hiển thị rationale cho permission
     */
    private fun showPermissionRationale(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        PermissionUtils.showCameraPermissionRationale(
            activity,
            onGranted = {
                Log.d(TAG, "User agreed to grant permission")
                requestCameraPermission()
            },
            onDenied = {
                Log.d(TAG, "User denied permission rationale")
                onDenied()
            }
        )
    }
    
    /**
     * Request camera permission
     */
    private fun requestCameraPermission() {
        Log.d(TAG, "Launching permission request")
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
    
    /**
     * Xử lý kết quả permission request
     */
    fun handlePermissionResult(
        isGranted: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        Log.d(TAG, "Permission result: $isGranted")
        
        if (isGranted) {
            Log.d(TAG, "Camera permission granted by user")
            onPermissionGranted()
        } else {
            Log.d(TAG, "Camera permission denied by user")
            handlePermissionDenied(onPermissionDenied)
        }
    }
    
    /**
     * Xử lý khi permission bị từ chối
     */
    private fun handlePermissionDenied(onPermissionDenied: () -> Unit) {
        // Kiểm tra xem user có chọn "Don't ask again" không
        if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Log.d(TAG, "User selected 'Don't ask again'")
            showPermissionDeniedDialog(onPermissionDenied)
        } else {
            Log.d(TAG, "User denied permission, can ask again")
            onPermissionDenied()
        }
    }
    
    /**
     * Hiển thị dialog khi permission bị từ chối vĩnh viễn
     */
    private fun showPermissionDeniedDialog(onPermissionDenied: () -> Unit) {
        PermissionUtils.showPermissionDeniedDialog(
            activity,
            onGoToSettings = {
                Log.d(TAG, "User chose to go to settings")
                PermissionUtils.openAppSettings(activity)
                onPermissionDenied()
            },
            onCancel = {
                Log.d(TAG, "User cancelled permission dialog")
                onPermissionDenied()
            }
        )
    }
}
