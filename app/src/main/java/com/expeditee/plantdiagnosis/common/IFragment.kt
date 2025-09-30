package com.expeditee.plantdiagnosis.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.expeditee.plantdiagnosis.helper.AppConfigSettings
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

typealias Inflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class IFragment<VB, VM, State>(private val inflate: Inflater<VB>) : Fragment()
        where VB : ViewBinding, VM : IViewModel<State>, State : IViewModel.IState {

    private val appConfigSettings by inject<AppConfigSettings>()

    private var _viewBinding: VB? = null
    protected val viewBinding: VB
        get() = _viewBinding!!

    abstract fun getLazyViewModel(): Lazy<VM>
    protected val viewModel: VM by this.getLazyViewModel()

    var isCreated: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInit()
    }

    protected open fun setupInit() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflate(inflater, container, false).also { _viewBinding = it }.root.let {
        if (it.parent != null) {
            ((it.parent) as ViewGroup).removeView(it)
        }
        return@let it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreated = true

        initViews()
        initObservers()
        initListeners()

        observerDeviceConnection()
    }

    abstract fun initViews()
    protected open fun initObservers() = Unit
    protected open fun initListeners() = Unit

    private fun observerDeviceConnection() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.networkState.collect { connected ->
                    onDeviceConnectionChanged(connected)
                }
            }
        }
    }

    protected open fun onDeviceConnectionChanged(connected: Boolean) = Unit

    protected fun withViewModels(block: VM.() -> Unit) {
        with(viewModel, block)
    }

    protected fun withViewBindings(block: VB.() -> Unit) {
        with(viewBinding, block)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isCreated = false
        _viewBinding = null
    }
}
