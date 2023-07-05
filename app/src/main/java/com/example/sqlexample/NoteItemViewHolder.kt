package com.example.sqlexample

import androidx.recyclerview.widget.RecyclerView
import com.example.sqlexample.databinding.NoteItemLayoutBinding

class NoteItemViewHolder(val binding: NoteItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
        binding.tvTitleRecyclerview.text = note.title
        binding.tvContentRecyclerview.text = note.content
    }
}