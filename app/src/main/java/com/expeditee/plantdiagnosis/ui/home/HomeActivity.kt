package com.expeditee.plantdiagnosis.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IActivity
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.ActivityHomeBinding
import com.expeditee.plantdiagnosis.extension.launchRepeatOnLifecycle
import com.expeditee.plantdiagnosis.extension.registerOnPageSelected
import com.expeditee.plantdiagnosis.extension.statusBars
import com.expeditee.plantdiagnosis.ui.askai.AskAiFragment
import com.expeditee.plantdiagnosis.ui.explore.ExploreFragment
import com.expeditee.plantdiagnosis.ui.settings.SettingsFragment
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * HomeActivity - Activity chính của ứng dụng Plant Diagnosis
 * 
 * Activity này quản lý navigation giữa 4 tab chính của ứng dụng sử dụng ViewPager2:
 * - Home: Trang chủ với danh sách cây trồng và lịch sử chẩn đoán
 * - Explore: Khám phá các loại cây và bệnh tật
 * - Ask AI: Tương tác với AI để chẩn đoán bệnh cây
 * - Settings: Cài đặt ứng dụng và tài khoản
 * 
 * Sử dụng ViewPager2 với HomePagerAdapter để quản lý các fragment và BottomNavigationView để điều hướng
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class HomeActivity : IActivity<ActivityHomeBinding, CommonViewModel, IViewModel.IState>() {

    companion object {
        private const val HOME_PAGE = HomePagerAdapter.HOME_PAGE
        private const val EXPLORE_PAGE = HomePagerAdapter.EXPLORE_PAGE
        private const val ASK_AI_PAGE = HomePagerAdapter.ASK_AI_PAGE
        private const val SETTINGS_PAGE = HomePagerAdapter.SETTINGS_PAGE
    }

    override fun isFitsSystemWindows(): Boolean = false
    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    override fun getLazyViewBinding() = lazy<ActivityHomeBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setupViewPager()
        setupBottomNavigation()
    }

    override fun initAds(block: (suspend CoroutineScope.() -> Unit)?) {
        super.initAds {
        }
    }

    private fun setupViewPager() {
        with(viewBinding.viewPage2) {
            adapter = HomePagerAdapter(this@HomeActivity)
            offscreenPageLimit = 3
            isUserInputEnabled = false
            registerOnPageSelected {
            }
        }
        setCurrentPage(HOME_PAGE)
    }

    private fun setupBottomNavigation() {
        viewBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    setCurrentPage(HOME_PAGE)
                    true
                }
                R.id.exploreFragment -> {
                    setCurrentPage(EXPLORE_PAGE)
                    true
                }
                R.id.askAiFragment -> {
                    setCurrentPage(ASK_AI_PAGE)
                    true
                }
                R.id.settingsFragment -> {
                    setCurrentPage(SETTINGS_PAGE)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentPage(page: Int) {
        viewBinding.viewPage2.setCurrentItem(page, false)
    }

    override fun initObservers() {
        super.initObservers()
        launchRepeatOnLifecycle {
        }
    }

    override fun initListeners() {
        super.initListeners()
    }
}
