package com.expeditee.plantdiagnosis.ui.askai

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IFragment
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.FragmentAskAiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskAiFragment : IFragment<FragmentAskAiBinding, CommonViewModel, IViewModel.IState>(
    FragmentAskAiBinding::inflate
) {
    
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    
    override fun initViews() {
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        viewBinding.rvChatMessages.layoutManager = LinearLayoutManager(context)
        // TODO: Setup chat messages adapter
    }
    
    private fun setupClickListeners() {
        viewBinding.btnSend.setOnClickListener {
            val message = viewBinding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                viewBinding.etMessage.text?.clear()
            }
        }
    }
    
    private fun sendMessage(message: String) {
        // TODO: Implement AI chat functionality
        showProgress(true)
        
        // Simulate AI response
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            showProgress(false)
            // Add AI response to chat
        }, 2000)
    }
    
    private fun showProgress(show: Boolean) {
        viewBinding.layoutProgress.visibility = if (show) View.VISIBLE else View.GONE
        viewBinding.btnSend.isEnabled = !show
    }
}
