package com.expeditee.plantdiagnosis.ui.settings

import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : IFragment<FragmentSettingsBinding, CommonViewModel, IViewModel.IState>(
    FragmentSettingsBinding::inflate
) {
    
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    
    override fun initViews() {
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // TODO: Setup settings click listeners
    }
}
