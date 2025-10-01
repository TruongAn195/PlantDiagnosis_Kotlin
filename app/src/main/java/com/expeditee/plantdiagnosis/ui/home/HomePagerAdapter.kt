package com.expeditee.plantdiagnosis.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expeditee.plantdiagnosis.ui.explore.ExploreFragment
import com.expeditee.plantdiagnosis.ui.dashboard.DashboardFragment
import com.expeditee.plantdiagnosis.ui.askai.AskAiFragment
import com.expeditee.plantdiagnosis.ui.settings.SettingsFragment

/**
 * HomePagerAdapter - Adapter cho ViewPager2 trong HomeActivity
 * 
 * Quản lý 4 fragment chính của ứng dụng:
 * - HOME_PAGE: HomeFragment - Trang chủ
 * - EXPLORE_PAGE: ExploreFragment - Khám phá
 * - ASK_AI_PAGE: AskAiFragment - Hỏi AI
 * - SETTINGS_PAGE: SettingsFragment - Cài đặt
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class HomePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        internal const val HOME_PAGE = 0
        internal const val DASHBOARD_PAGE = 1
        internal const val EXPLORE_PAGE = 2
        internal const val ASK_AI_PAGE = 3
        internal const val SETTINGS_PAGE = 4
    }

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME_PAGE -> HomeFragment()
            DASHBOARD_PAGE -> DashboardFragment()
            EXPLORE_PAGE -> ExploreFragment()
            ASK_AI_PAGE -> AskAiFragment()
            SETTINGS_PAGE -> SettingsFragment()
            else -> HomeFragment()
        }
    }
}

