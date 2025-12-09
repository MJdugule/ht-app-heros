package com.hellotractor.android.notes.farms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hellotractor.notes.domain.models.Note
import com.hellotractor.notes.presentation.databinding.ItemFarmBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class FarmsAdapter(private val onItemClick: (Note) -> Unit) : ListAdapter<Note, FarmsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemFarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(private val binding: ItemFarmBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.farmTitle.text = note.title
            val formattedDate = try {
                val date = inputFormat.parse(note.dateCreated)
                outputFormat.format(date)
            } catch (e: ParseException) {
                note.dateCreated // Fallback to the original string if parsing fails
            }
            binding.farmSubtitle.text = "${note.type.name} • $formattedDate"
            binding.farmContent.text = note.content
        }
    }

    companion object {
        private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        private val outputFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())

        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
        }
    }
}
