package com.expeditee.plantdiagnosis.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expeditee.plantdiagnosis.ui.explore.ExploreFragment
import com.expeditee.plantdiagnosis.ui.askai.AskAiFragment
import com.expeditee.plantdiagnosis.ui.settings.SettingsFragment

class HomePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val HOME_PAGE = 0
        const val EXPLORE_PAGE = 1
        const val ASK_AI_PAGE = 2
        const val SETTINGS_PAGE = 3
    }

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME_PAGE -> HomeFragment()
            EXPLORE_PAGE -> ExploreFragment()
            ASK_AI_PAGE -> AskAiFragment()
            SETTINGS_PAGE -> SettingsFragment()
            else -> HomeFragment()
        }
    }
}

