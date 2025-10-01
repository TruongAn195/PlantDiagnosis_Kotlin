package com.expeditee.plantdiagnosis.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.databinding.FragmentDashboardBinding
import com.expeditee.plantdiagnosis.extension.launchRepeatOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : IFragment<FragmentDashboardBinding, DashboardViewModel, DashboardViewModel.DashboardState>(
    { inflater, container, attachToParent ->
        DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater, R.layout.fragment_dashboard, container, attachToParent
        )
    }
) {

    override fun getLazyViewModel() = viewModel<DashboardViewModel>()

    override fun initViews() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this
    }

    override fun initObservers() {
        super.initObservers()
        launchRepeatOnLifecycle {
            viewModel.dashboardState.collect { state ->
                updateUI(state)
            }
        }
    }

    override fun initListeners() {
        super.initListeners()
    }

    private fun updateUI(state: DashboardViewModel.DashboardState) {
        if (state.isLoading) {
            // Show loading state
        } else {
            // Update UI with data
        }
    }
}
