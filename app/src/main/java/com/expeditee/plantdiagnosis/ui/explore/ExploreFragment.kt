package com.expeditee.plantdiagnosis.ui.explore

import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentExploreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : IFragment<FragmentExploreBinding, CommonViewModel, IViewModel.IState>(
    FragmentExploreBinding::inflate
) {
    
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    
    override fun initViews() {
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // TODO: Setup click listeners for explore options
    }
}
