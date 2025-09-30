package com.expeditee.plantdiagnosis.ui.home

import android.util.Log
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

/**
 * HomeFragment - Fragment trang chủ của ứng dụng
 * 
 * Hiển thị thông tin chào mừng và các chức năng chính của ứng dụng
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class HomeFragment : IFragment<FragmentHomeBinding, CommonViewModel, IViewModel.IState>(
    FragmentHomeBinding::inflate
) {
    
    companion object {
        private const val TAG = "HomeFragment"
    }
    
    override fun getLazyViewModel() = lazy { 
        inject<CommonViewModel>().value 
    }
    
    override fun initViews() {
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        viewBinding.cardDiagnose.setOnClickListener {
        }
        
        viewBinding.cardHistory.setOnClickListener {
        }
        
        viewBinding.cardExplore.setOnClickListener {
        }
        
        viewBinding.cardAskAi.setOnClickListener {
        }
    }
}