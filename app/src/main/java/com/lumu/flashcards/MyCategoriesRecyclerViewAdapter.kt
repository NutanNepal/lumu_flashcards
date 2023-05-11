package com.lumu.flashcards

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lumu.flashcards.databinding.FragmentCategoryBinding

class MyCategoriesRecyclerViewAdapter(
    private val values: List<Category>,
    private val onItemClickListener: OnCategoryItemClickListener
) : RecyclerView.Adapter<MyCategoriesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = values[position]
        holder.idView.text = buildString {
        append(position + 1)
        append(".")
    }
        holder.contentView.text = category.toString()
        holder.itemView.setOnClickListener {
            onItemClickListener.onCategoryItemClick(category)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

    interface OnCategoryItemClickListener {
        fun onCategoryItemClick(category: Category)
    }
}

