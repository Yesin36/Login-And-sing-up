package com.example.loginandsignupusingfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandsignupusingfirebase.databinding.NotesitemBinding

class noteAdapter(private val notes: List<NoteItem>, private val itemClickListener: OnItemClickListener): RecyclerView.Adapter<noteAdapter.NoteViewHolder>() {


    interface OnItemClickListener {
        fun onDeleteClick(noteId: String)
        fun onUpdateClick(noteId: String, title: String, description: String)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)

    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note: NoteItem = notes[position]
        holder.bind(note)

        // button work
        holder.binding.update.setOnClickListener {
            itemClickListener.onUpdateClick(note.noteId,note.title,note.description)
        }
        holder.binding.delete.setOnClickListener {
            itemClickListener.onDeleteClick(note.noteId)
        }
    }

    override fun getItemCount(): Int {

        return notes.size

    }

    class NoteViewHolder(val binding:NotesitemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteItem) {

            binding.titleTextView.text = note.title
            binding.descriptionTextView.text = note.description

        }

    }
}