package com.expeditee.plantdiagnosis.common
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Locale

typealias OnPerformBackPressed = () -> Unit

abstract class IActivity<VB, VM, State> : AppCompatActivity()
        where VB : ViewDataBinding, VM : IViewModel<State>, State : IViewModel.IState {

    protected val viewModel: VM by this.getLazyViewModel()
    abstract fun getLazyViewModel(): Lazy<VM>

    protected val viewBinding: VB by this.getLazyViewBinding()
    abstract fun getLazyViewBinding(): Lazy<VB>

    private var onPerformBackPressed: OnPerformBackPressed? = null
    private var onBackPressedCallback = object : OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            onPerformBackPressed?.invoke()
        }
    }

    private val connectivityManager: ConnectivityManager? by lazy {
        ContextCompat.getSystemService(this@IActivity, ConnectivityManager::class.java)
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            runOnUiThread {
                viewModel.setNetworkState(true)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            runOnUiThread {
                viewModel.setNetworkState(false)
            }
        }
    }

    protected val TAG: String
        get() = this::class.java.simpleName

    override fun attachBaseContext(newBase: Context) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupInit()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewBinding.lifecycleOwner = this@IActivity
        makeDarkStatusBars()
        registerBackPressedDispatcher()

        initAds()
        initViews(savedInstanceState)
        initObservers()
        initListeners()


    }

    protected open fun isLightStatusBar(): Boolean = true
    private fun makeDarkStatusBars() {
        WindowCompat.setDecorFitsSystemWindows(window, isFitsSystemWindows())
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.isAppearanceLightStatusBars = isLightStatusBar()
            controller.isAppearanceLightNavigationBars = isLightStatusBar()
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    protected open fun isFitsSystemWindows(): Boolean = true
    protected open fun isHideSystemBars(): Boolean = false

    protected fun applyInsetToView(
        targetView: View = viewBinding.root,
        block: (view: View, insets: WindowInsetsCompat) -> Unit
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(targetView) { view, insetCompact ->
            block(view, insetCompact)
            return@setOnApplyWindowInsetsListener WindowInsetsCompat.CONSUMED
        }
    }

    private fun registerBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(this@IActivity, onBackPressedCallback)
        onBackPressedCallback.isEnabled = onHandleBackPressed()
    }

    @CallSuper
    protected open fun onHandleBackPressed(onBackPressed: OnPerformBackPressed? = null): Boolean {
        this.onPerformBackPressed = onBackPressed
        return onBackPressed != null
    }

    protected open fun setupInit() = Unit

    @CallSuper
    protected open fun initAds(block: (suspend CoroutineScope.() -> Unit)? = null) {
        block?.let {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    it.invoke(this)
                }
            }
        }
    }

    abstract fun initViews(savedInstanceState: Bundle?)

    @CallSuper
    protected open fun initObservers() {
        viewModel
    }

    protected open fun initListeners() = Unit


    protected open fun onBillingPurchaseListener(hasPurchase: Boolean) = Unit

    protected fun registerNetworkCallback() {
        connectivityManager?.runCatching {
            try {
                val request = NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
                    .build()
                requestNetwork(request, networkCallback)
            } catch (e: Exception) {
                registerDefaultNetworkCallback(networkCallback)
            }
        }
    }

    protected fun unregisterNetworkCallback() {
        connectivityManager?.runCatching {
            unregisterNetworkCallback(networkCallback)
        }
    }


    @CallSuper
    override fun onStart() {
        Handler(Looper.getMainLooper()).postDelayed({
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                if (isHideSystemBars()) {
                    controller.hide(WindowInsetsCompat.Type.systemBars())
                } else {
                    controller.show(WindowInsetsCompat.Type.statusBars())
                    controller.hide(WindowInsetsCompat.Type.navigationBars())
                }
            }
        }, 50L)
        super.onStart()
    }

    override fun onDestroy() {
        unregisterNetworkCallback()
        super.onDestroy()
    }
}
