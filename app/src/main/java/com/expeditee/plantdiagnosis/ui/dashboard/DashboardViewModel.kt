package com.expeditee.plantdiagnosis.ui.dashboard

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.expeditee.plantdiagnosis.common.IViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : IViewModel<DashboardViewModel.DashboardState>(application) {

    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()

    override fun onState(state: DashboardState) {
        _dashboardState.value = state
    }

    fun loadDashboardData() {
        launchBlock {
            onState(DashboardState(isLoading = true))
            
            kotlinx.coroutines.delay(1000)
            
            onState(DashboardState(
                isLoading = false,
                totalPlants = 15,
                recentDiagnoses = 8,
                successRate = 85.5f
            ))
        }
    }

    data class DashboardState(
        val isLoading: Boolean = false,
        val totalPlants: Int = 0,
        val recentDiagnoses: Int = 0,
        val successRate: Float = 0f,
        val error: String? = null
    ) : IState
}
