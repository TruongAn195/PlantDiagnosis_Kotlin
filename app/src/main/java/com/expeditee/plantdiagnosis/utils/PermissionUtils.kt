package com.expeditee.plantdiagnosis.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.expeditee.plantdiagnosis.R

/**
 * Utility class để kiểm tra và xử lý permissions
 */
object PermissionUtils {
    
    /**
     * Kiểm tra xem app có quyền camera không
     */
    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Kiểm tra xem app có quyền đọc storage không
     */
    fun hasStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Kiểm tra xem app có quyền đọc media images không (Android 13+)
     */
    fun hasMediaImagesPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Kiểm tra xem có cần hiển thị rationale cho permission không
     */
    fun shouldShowRequestPermissionRationale(
        activity: androidx.fragment.app.FragmentActivity,
        permission: String
    ): Boolean {
        return activity.shouldShowRequestPermissionRationale(permission)
    }
    
    /**
     * Hiển thị dialog giải thích tại sao cần permission camera
     */
    fun showCameraPermissionRationale(
        context: Context,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle("Camera Permission Required")
            .setMessage("This app needs camera access to identify plants and diagnose diseases. Please grant camera permission to continue.")
            .setPositiveButton("Grant Permission") { _, _ ->
                onGranted()
            }
            .setNegativeButton("Cancel") { _, _ ->
                onDenied()
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * Hiển thị dialog hướng dẫn user vào Settings để cấp quyền
     */
    fun showPermissionDeniedDialog(
        context: Context,
        onGoToSettings: () -> Unit,
        onCancel: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle("Permission Denied")
            .setMessage("Camera permission is required to use this feature. Please go to Settings and grant camera permission manually.")
            .setPositiveButton("Go to Settings") { _, _ ->
                onGoToSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                onCancel()
            }
            .setCancelable(false)
            .show()
    }
    
    /**
     * Mở Settings để user có thể cấp quyền thủ công
     */
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
    
    /**
     * Kiểm tra tất cả permissions cần thiết cho camera
     */
    fun hasAllCameraPermissions(context: Context): Boolean {
        return hasCameraPermission(context) && 
               (hasStoragePermission(context) || hasMediaImagesPermission(context))
    }
}
