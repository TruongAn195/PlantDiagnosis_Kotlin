package com.expeditee.plantdiagnosis.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : IFragment<FragmentHomeBinding, CommonViewModel, IViewModel.IState>(
    FragmentHomeBinding::inflate
) {
    
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    
    override fun initViews() {
        android.util.Log.d("HomeFragment", "initViews called")
        setupRecyclerView()
        android.util.Log.d("HomeFragment", "HomeFragment initialized successfully")
    }
    
    private fun setupRecyclerView() {
        // TODO: Setup recent scans recycler view
        viewBinding.rvRecentScans.layoutManager = LinearLayoutManager(context)
        // viewBinding.rvRecentScans.adapter = RecentScansAdapter()
    }
}
