package com.b00sti.notes.ui.notes

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.b00sti.notes.R
import com.b00sti.notes.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by b00sti on 05.06.2018
 */
private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.timestamp == newItem.timestamp
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
}

class NotesAdapter(private val clickListener: (Note) -> Unit) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater.inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note, clickListener: (Note) -> Unit) {
            itemView.tvDescription.text = "description: " + note.desc
            itemView.tvAddingTime.text = "Time: " + note.timestamp
            itemView.tvTags.text = "Tags: " + note.tag
            itemView.setOnClickListener { clickListener(note) }
        }
    }
}