package com.expeditee.plantdiagnosis.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.IActivity
import com.expeditee.plantdiagnosis.databinding.ActivityCameraBinding
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : IActivity<ActivityCameraBinding, CameraViewModel, CameraViewModel.CameraState>() {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraProvider: ProcessCameraProvider
    private var camera: Camera? = null

    override fun getLazyViewModel() = viewModel<CameraViewModel>()

    override fun getLazyViewBinding() = lazy<ActivityCameraBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_camera)
    }
    override fun isFitsSystemWindows() = false

    override fun initViews(savedInstanceState: Bundle?) {
        setupToolbars()
        setupTabLayout()
        setupCamera()
        cameraExecutor = Executors.newSingleThreadExecutor()
        checkCameraPermission()
    }


    override fun initListeners() {
        super.initListeners()

        viewBinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewBinding.ivGallery.setOnClickListener {
            openGallery()
        }

        viewBinding.ivCameraShutter.setOnClickListener {
            takePicture()
        }
    }

    override fun initObservers() {
        super.initObservers()
    }

    private fun setupTabLayout() {
        viewBinding.tabLayoutModes.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun checkCameraPermission() { }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                viewBinding.cameraPreviewView.doOnLayout {
                    bindCameraUseCases()
                }
            } catch (_: Exception) {}
        }, ContextCompat.getMainExecutor(this))
    }


    private fun bindCameraUseCases() {
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(viewBinding.cameraPreviewView.surfaceProvider)
            }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture
            )
        } catch (exc: Exception) { }
    }

    private fun takePicture() {
        if (!::imageCapture.isInitialized) return

        val photoFile = File(
            getExternalFilesDir(null),
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    navigateToCrop(savedUri, "camera")
                }
            }
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                navigateToCrop(uri, "gallery")
            }
        }
    }

    private fun navigateToCrop(imageUri: Uri, imageSource: String) { }


    private fun setupToolbars() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowTitleEnabled(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}