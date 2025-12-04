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
import java.text.SimpleDateFormat
import java.util.Locale

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
        private val content: TextView = itemView.findViewById(R.id.farm_content)

        fun bind(note: Note) {
            title.text = note.title
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(note.dateCreated)
            val formattedDate = outputFormat.format(date)
            subtitle.text = "${note.type.name} • $formattedDate"
            content.text = note.content
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
        }
    }
}
