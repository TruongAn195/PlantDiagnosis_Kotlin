package com.expeditee.plantdiagnosis.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expeditee.plantdiagnosis.databinding.ItemOnboardingBinding

class OnboardingAdapter(
    private val items: List<OnboardingItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    
    class OnboardingViewHolder(private val binding: ItemOnboardingBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: OnboardingItem, position: Int, onItemClick: (Int) -> Unit) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.ivIcon.setImageResource(item.iconRes)
            
            binding.root.setOnClickListener {
                onItemClick(position)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnboardingViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position], position, onItemClick)
    }
    
    override fun getItemCount(): Int = items.size
}
