package com.example.loginandsignupusingfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignupusingfirebase.databinding.NotesitemBinding

class noteAdapter(private val notes: List<NoteItem>): RecyclerView.Adapter<noteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)

    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note: NoteItem = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {

        return notes.size

    }

    class NoteViewHolder(private val binding:NotesitemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {

            binding.titleTextView.text = note.title
            binding.descriptionTextView.text = note.description

        }

    }
}