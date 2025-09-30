package com.expeditee.plantdiagnosis.ui.settings

import android.util.Log
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

/**
 * SettingsFragment - Fragment Account của ứng dụng PlantPro
 * 
 * Hiển thị thông tin tài khoản và các tùy chọn cài đặt
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class SettingsFragment : IFragment<FragmentSettingsBinding, CommonViewModel, IViewModel.IState>(
    FragmentSettingsBinding::inflate
) {
    
    companion object {
        private const val TAG = "SettingsFragment"
    }
    
    override fun getLazyViewModel() = lazy { 
        inject<CommonViewModel>().value 
    }
    
    override fun initViews() {
        Log.d(TAG, "initViews called")
        setupClickListeners()
    }
    
    /**
     * Thiết lập các click listener cho Account screen
     */
    private fun setupClickListeners() {
        // Header actions
        viewBinding.ivBack.setOnClickListener {
            // Navigate back
        }
        
        viewBinding.ivSettings.setOnClickListener {
            // Open settings
        }
        
        // User profile
        viewBinding.cardUserProfile.setOnClickListener {
            // Navigate to profile details
        }

        
        // Personalization section
        viewBinding.llProfile.setOnClickListener {
            // Navigate to profile settings
        }
        
        viewBinding.llAppAppearance.setOnClickListener {
            // Navigate to app appearance settings
        }
        
        viewBinding.llNotifications.setOnClickListener {
            // Navigate to notification settings
        }
        
        viewBinding.llSnapPreference.setOnClickListener {
            // Navigate to snap preference settings
        }
        
        // Account section
        viewBinding.llAccountSecurity.setOnClickListener {
            // Navigate to account & security
        }

        
        Log.d(TAG, "Click listeners setup completed")
    }
}