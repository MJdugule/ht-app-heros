package com.hellotractor.android.notes.farms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hellotractor.notes.domain.models.Note
import com.hellotractor.android.notes.R

class FarmsAdapter : ListAdapter<Note, FarmsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_farm, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.farm_title)
        private val subtitle: TextView = itemView.findViewById(R.id.farm_subtitle)

        fun bind(note: Note) {
            title.text = note.title
            subtitle.text = "${note.type.name} • ${note.dateCreated}"
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
        }
    }
}
