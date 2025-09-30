package com.expeditee.plantdiagnosis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expeditee.plantdiagnosis.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val languages: List<LanguageActivity.LanguageItem>,
    private val onLanguageSelected: (LanguageActivity.LanguageItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(languages[position])
    }

    override fun getItemCount(): Int = languages.size

    inner class LanguageViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(language: LanguageActivity.LanguageItem) {
            binding.apply {
                tvLanguageName.text = language.displayName
                tvLanguageDescription.text = language.name
                
                root.setOnClickListener {
                    onLanguageSelected(language)
                }
            }
        }
    }
}
