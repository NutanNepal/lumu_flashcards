package com.lumu.flashcards

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lumu.flashcards.databinding.FragmentChaptersBinding

class MyChaptersRecyclerViewAdapter(
    category: Category,
    private val onItemClickListener: OnChapterItemClickListener
) : RecyclerView.Adapter<MyChaptersRecyclerViewAdapter.ViewHolder>() {
    private val values = category.chapterList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentChaptersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = values[position]
        holder.idView.text = buildString {
            append((position+1).toString())
            append('.')
        }
        holder.contentView.text = chapter
        holder.itemView.setOnClickListener {
            onItemClickListener.onChapterItemClick(chapter)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentChaptersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }
    interface OnChapterItemClickListener {
        fun onChapterItemClick(chapter: String)
    }
}