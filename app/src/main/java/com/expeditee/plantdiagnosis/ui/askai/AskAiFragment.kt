package com.expeditee.plantdiagnosis.ui.askai

import android.util.Log
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentAskAiBinding
import org.koin.android.ext.android.inject

/**
 * AskAiFragment - Fragment hỏi AI của ứng dụng
 * 
 * Hiển thị giao diện chat với AI để chẩn đoán bệnh cây
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class AskAiFragment : IFragment<FragmentAskAiBinding, CommonViewModel, IViewModel.IState>(
    FragmentAskAiBinding::inflate
) {
    
    companion object {
        private const val TAG = "AskAiFragment"
    }
    
    override fun getLazyViewModel() = lazy { 
        inject<CommonViewModel>().value 
    }
    
    override fun initViews() {
        Log.d(TAG, "initViews called")
        setupClickListeners()
    }
    
    /**
     * Thiết lập các click listener cho chat AI
     */
    private fun setupClickListeners() {
        // TODO: Thêm click listeners cho chat AI
        Log.d(TAG, "Click listeners setup completed")
    }
}