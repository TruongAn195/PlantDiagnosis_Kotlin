package com.expeditee.plantdiagnosis.ui.home

import android.os.Bundle
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.common.CommonViewModel
import com.expeditee.plantdiagnosis.common.IActivity
import com.expeditee.plantdiagnosis.common.IViewModel
import com.expeditee.plantdiagnosis.databinding.ActivityMainBinding
import com.expeditee.plantdiagnosis.extension.registerOnPageSelected
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : IActivity<ActivityMainBinding, CommonViewModel, IViewModel.IState>() {

    companion object {
        private const val HOME_PAGE = HomePagerAdapter.HOME_PAGE
        private const val EXPLORE_PAGE = HomePagerAdapter.EXPLORE_PAGE
        private const val ASK_AI_PAGE = HomePagerAdapter.ASK_AI_PAGE
        private const val SETTINGS_PAGE = HomePagerAdapter.SETTINGS_PAGE
    }

    override fun getLazyViewModel() = viewModel<CommonViewModel>()
    override fun getLazyViewBinding() = lazy { 
        android.util.Log.d("MainActivity", "Creating ViewBinding")
        ActivityMainBinding.inflate(layoutInflater) 
    }

    override fun initViews(savedInstanceState: Bundle?) {
        android.util.Log.d("MainActivity", "initViews called")
        setupViewPager()
        setupBottomNavigation()
    }

    private fun setupViewPager() {
        android.util.Log.d("MainActivity", "setupViewPager called")
        with(viewBinding.viewPage2) {
            adapter = HomePagerAdapter(this@MainActivity)
            offscreenPageLimit = 3
            isUserInputEnabled = false
            registerOnPageSelected {
                android.util.Log.d("MainActivity", "Page selected: $it")
            }
        }
        setCurrentPage(HOME_PAGE)
        android.util.Log.d("MainActivity", "ViewPager setup completed")
    }

    private fun setCurrentPage(page: Int) {
        viewBinding.viewPage2.setCurrentItem(page, false)
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

}