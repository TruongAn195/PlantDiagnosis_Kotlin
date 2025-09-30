package com.expeditee.plantdiagnosis.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.ui.askai.AskAiFragment
import com.expeditee.plantdiagnosis.ui.explore.ExploreFragment
import com.expeditee.plantdiagnosis.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity - Activity chính của ứng dụng Plant Diagnosis
 * 
 * Activity này quản lý navigation giữa 4 tab chính của ứng dụng:
 * - Home: Trang chủ với danh sách cây trồng và lịch sử chẩn đoán
 * - Explore: Khám phá các loại cây và bệnh tật
 * - Ask AI: Tương tác với AI để chẩn đoán bệnh cây
 * - Settings: Cài đặt ứng dụng và tài khoản
 * 
 * Sử dụng FragmentContainerView để quản lý các fragment và BottomNavigationView để điều hướng
 * 
 * @author Plant Diagnosis Team
 * @since 1.0.0
 */
class MainActivity : AppCompatActivity() {

    companion object {
        // Các hằng số định nghĩa vị trí tab
        const val HOME_PAGE = 0      // Trang chủ
        const val EXPLORE_PAGE = 1   // Trang khám phá  
        const val ASK_AI_PAGE = 2    // Trang hỏi AI
        const val SETTINGS_PAGE = 3  // Trang cài đặt
        
        private const val TAG = "MainActivity"
    }

    // Fragment hiện tại
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Log.d(TAG, "onCreate called")
        setupBottomNavigation() // Thiết lập BottomNavigationView
        showHomeFragment()      // Hiển thị fragment Home mặc định
    }

    /**
     * Thiết lập BottomNavigationView với các listener xử lý sự kiện click
     * Khi user click vào item trong bottom navigation, sẽ chuyển đến fragment tương ứng
     */
    private fun setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation called")
        
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            val selectedPage = when (item.itemId) {
                R.id.homeFragment -> HOME_PAGE
                R.id.exploreFragment -> EXPLORE_PAGE
                R.id.askAiFragment -> ASK_AI_PAGE
                R.id.settingsFragment -> SETTINGS_PAGE
                else -> return@setOnItemSelectedListener false
            }
            
            showFragment(selectedPage)
            true
        }
        
        // Thiết lập tab Home được chọn mặc định
        bottomNav.selectedItemId = R.id.homeFragment
        
        Log.d(TAG, "BottomNavigation setup completed")
    }

    /**
     * Hiển thị fragment tương ứng với tab được chọn
     * 
     * @param page Vị trí tab cần hiển thị (HOME_PAGE, EXPLORE_PAGE, ASK_AI_PAGE, SETTINGS_PAGE)
     */
    private fun showFragment(page: Int) {
        Log.d(TAG, "showFragment called with page: $page")
        
        val fragment = when (page) {
            HOME_PAGE -> HomeFragment()
            EXPLORE_PAGE -> ExploreFragment()
            ASK_AI_PAGE -> AskAiFragment()
            SETTINGS_PAGE -> SettingsFragment()
            else -> HomeFragment()
        }
        
        // Chỉ thay thế fragment nếu khác với fragment hiện tại
        if (currentFragment?.javaClass != fragment.javaClass) {
            try {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
                
                currentFragment = fragment
                Log.d(TAG, "Fragment replaced: ${fragment.javaClass.simpleName}")
            } catch (e: Exception) {
                Log.e(TAG, "Error replacing fragment: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    /**
     * Hiển thị HomeFragment mặc định khi khởi động
     */
    private fun showHomeFragment() {
        Log.d(TAG, "showHomeFragment called")
        showFragment(HOME_PAGE)
    }

    /**
     * Xử lý khi user nhấn nút Back
     * Nếu không phải ở tab Home, sẽ quay về tab Home
     * Nếu đã ở tab Home, sẽ thoát ứng dụng
     */
    override fun onBackPressed() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        if (bottomNav.selectedItemId != R.id.homeFragment) {
            bottomNav.selectedItemId = R.id.homeFragment
        } else {
            super.onBackPressed()
        }
    }
}