package com.expeditee.plantdiagnosis

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.expeditee.plantdiagnosis.R
import com.expeditee.plantdiagnosis.databinding.ActivityOnboardingBinding
import com.expeditee.plantdiagnosis.ui.home.HomeActivity
import com.expeditee.plantdiagnosis.ui.onboarding.OnboardingAdapter
import com.expeditee.plantdiagnosis.ui.onboarding.OnboardingItem

class OnboardingActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private lateinit var prefs: SharedPreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        setupOnboarding()
    }
    
    private fun setupOnboarding() {
        val onboardingItems = listOf(
            OnboardingItem(
                title = getString(R.string.onboarding_identify_title),
                description = getString(R.string.onboarding_identify_desc),
                illustrationRes = R.drawable.ic_onboarding_identify
            ),
            OnboardingItem(
                title = getString(R.string.onboarding_learn_title),
                description = getString(R.string.onboarding_learn_desc),
                illustrationRes = R.drawable.ic_onboarding_learn
            ),
            OnboardingItem(
                title = getString(R.string.onboarding_articles_title),
                description = getString(R.string.onboarding_articles_desc),
                illustrationRes = R.drawable.ic_onboarding_articles
            )
        )
        
        adapter = OnboardingAdapter(onboardingItems) { position ->
            when (position) {
                0, 1 -> binding.viewPager.currentItem = position + 1
                2 -> completeOnboarding()
            }
        }
        
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUI(position)
                updateDotsIndicator(position)
            }
        })
        
        setupDotsIndicator()
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                completeOnboarding()
            }
        }
    }
    
    private fun updateUI(position: Int) {
        binding.btnNext.text = if (position == 2) {
            getString(R.string.onboarding_signup)
        } else {
            getString(R.string.onboarding_next)
        }
    }
    
    
    private fun setupDotsIndicator() {
        val dots = arrayOfNulls<ImageView>(3)
        
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val dotSize = 12
            val dotMargin = 8
            
            val params = LinearLayout.LayoutParams(dotSize, dotSize)
            params.setMargins(dotMargin, 0, dotMargin, 0)
            dots[i]?.layoutParams = params
            
            binding.dotsIndicator.addView(dots[i])
        }
        
        updateDotsIndicator(0)
    }
    
    private fun updateDotsIndicator(position: Int) {
        val childCount = binding.dotsIndicator.childCount
        for (i in 0 until childCount) {
            val dot = binding.dotsIndicator.getChildAt(i) as ImageView
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setSize(24, 24)
                setColor(ContextCompat.getColor(this@OnboardingActivity, 
                    if (i == position) R.color.primary_green else R.color.light_gray))
            }
            dot.setImageDrawable(drawable)
        }
    }
    
    private fun completeOnboarding() {
        prefs.edit().putBoolean("has_completed_onboarding", true).apply()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
