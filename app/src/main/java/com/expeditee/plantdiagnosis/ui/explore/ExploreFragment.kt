package com.expeditee.plantdiagnosis.ui.explore

import android.util.Log
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentExploreBinding
import org.koin.android.ext.android.inject

/**
 * ExploreFragment - Fragment khám phá của ứng dụng
 * 
 * Hiển thị các chức năng khám phá cây trồng và bệnh tật
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class ExploreFragment : IFragment<FragmentExploreBinding, CommonViewModel, IViewModel.IState>(
    FragmentExploreBinding::inflate
) {
    
    companion object {
        private const val TAG = "ExploreFragment"
    }
    
    override fun getLazyViewModel() = lazy { 
        inject<CommonViewModel>().value 
    }
    
    override fun initViews() {
        Log.d(TAG, "initViews called")
        setupClickListeners()
    }
    
    /**
     * Thiết lập các click listener cho các chức năng khám phá
     */
    private fun setupClickListeners() {
        // TODO: Thêm click listeners cho các chức năng khám phá
        Log.d(TAG, "Click listeners setup completed")
    }
}