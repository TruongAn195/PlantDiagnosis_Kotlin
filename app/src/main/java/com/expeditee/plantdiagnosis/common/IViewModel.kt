package com.expeditee.plantdiagnosis.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.expeditee.plantdiagnosis.helper.AppConfigSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class IViewModel<State : IViewModel.IState>(application: Application) :
    AndroidViewModel(application), KoinComponent {

    protected val appConfigSettings: AppConfigSettings by inject<AppConfigSettings>()
    protected val applicationContext: Context
        get() = getApplication<Application>().applicationContext

    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("IViewModel: with $context: $throwable")
        android.util.Log.e("IViewModel", "Coroutine exception: $throwable", throwable)
    }

    private val _purchaseState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    internal var purchaseState: StateFlow<Boolean> = _purchaseState.asStateFlow()
    internal val purchaseStateValue: Boolean
        get() = purchaseState.value

    private val _networkState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    internal val networkState: StateFlow<Boolean> = _networkState.asStateFlow()
    internal val networkStateValue: Boolean
        get() = networkState.value

    fun setNetworkState(isConnected: Boolean) {
        _networkState.value = isConnected
    }

    protected fun launchBlock(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            context = dispatcher + coroutineExceptionHandler,
            start = start,
            block = block
        )
    }

    abstract fun onState(state: State)

    interface IState

    override fun onCleared() {
        super.onCleared()
    }
}

class CommonViewModel(application: Application) : IViewModel<IViewModel.IState>(application) {

    internal var introCompleted: Boolean
        get() = appConfigSettings.introCompleted
        set(value) {
            appConfigSettings.introCompleted = value
        }


    override fun onState(state: IState) {
        /* no-op */
    }
}
