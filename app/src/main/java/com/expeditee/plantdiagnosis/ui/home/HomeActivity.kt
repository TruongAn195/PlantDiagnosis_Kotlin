package com.expeditee.plantdiagnosis.ui.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IActivity
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.ActivityHomeBinding
import com.expeditee.plantdiagnosis.extension.launchRepeatOnLifecycle
import com.expeditee.plantdiagnosis.extension.registerOnPageSelected
import com.expeditee.plantdiagnosis.extension.statusBars
import com.expeditee.plantdiagnosis.ui.askai.AskAiFragment
import com.expeditee.plantdiagnosis.ui.camera.CameraActivity
import com.expeditee.plantdiagnosis.ui.explore.ExploreFragment
import com.expeditee.plantdiagnosis.ui.settings.SettingsFragment
import com.expeditee.plantdiagnosis.utils.CameraPermissionHandler
import com.expeditee.plantdiagnosis.utils.PermissionUtils
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : IActivity<ActivityHomeBinding, CommonViewModel, IViewModel.IState>() {

    companion object {
        private const val HOME_PAGE = HomePagerAdapter.HOME_PAGE
        private const val EXPLORE_PAGE = HomePagerAdapter.EXPLORE_PAGE
        private const val ASK_AI_PAGE = HomePagerAdapter.ASK_AI_PAGE
        private const val SETTINGS_PAGE = HomePagerAdapter.SETTINGS_PAGE
        private const val TAG = "HomeActivity"
    }
    
    // Camera permission handler
    private lateinit var cameraPermissionHandler: CameraPermissionHandler

    override fun isFitsSystemWindows(): Boolean = false
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    override fun getLazyViewBinding() = lazy<ActivityHomeBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setupViewPager()
        setupBottomNavigation()
        setupCameraPermissionHandler()
    }

    override fun initAds(block: (suspend CoroutineScope.() -> Unit)?) {
        super.initAds {
        }
    }

    private fun setupViewPager() {
        with(viewBinding.viewPage2) {
            adapter = HomePagerAdapter(this@HomeActivity)
            offscreenPageLimit = 3
            isUserInputEnabled = false
            registerOnPageSelected {
            }
        }
        setCurrentPage(HOME_PAGE)
    }

    private fun setupBottomNavigation() {
        viewBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    setCurrentPage(HOME_PAGE)
                    true
                }
                R.id.exploreFragment -> {
                    setCurrentPage(EXPLORE_PAGE)
                    true
                }
                R.id.askAiFragment -> {
                    setCurrentPage(ASK_AI_PAGE)
                    true
                }
                R.id.settingsFragment -> {
                    setCurrentPage(SETTINGS_PAGE)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentPage(page: Int) {
        viewBinding.viewPage2.setCurrentItem(page, false)
    }

    override fun initObservers() {
        super.initObservers()
        launchRepeatOnLifecycle {
        }
    }

    override fun initListeners() {
        super.initListeners()
    }
    
    /**
     * Thiết lập camera permission handler
     */
    private fun setupCameraPermissionHandler() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            cameraPermissionHandler.handlePermissionResult(
                isGranted,
                onPermissionGranted = {
                    Log.d(TAG, "Camera permission granted, opening camera")
                    openCameraActivity()
                },
                onPermissionDenied = {
                    Log.d(TAG, "Camera permission denied")
                    // Có thể hiển thị thông báo hoặc làm gì đó khác
                }
            )
        }
        
        cameraPermissionHandler = CameraPermissionHandler(this, permissionLauncher)
    }
    
    /**
     * Kiểm tra permission và mở camera activity
     */
    fun openCameraWithPermissionCheck() {
        Log.d(TAG, "Checking camera permission before opening camera")
        cameraPermissionHandler.checkAndRequestCameraPermission(
            onPermissionGranted = {
                Log.d(TAG, "Camera permission already granted, opening camera")
                openCameraActivity()
            },
            onPermissionDenied = {
                Log.d(TAG, "Camera permission denied by user")
                // Có thể hiển thị thông báo hoặc làm gì đó khác
            }
        )
    }
    
    /**
     * Mở camera activity
     */
    private fun openCameraActivity() {
        Log.d(TAG, "Opening camera activity")
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }
}
