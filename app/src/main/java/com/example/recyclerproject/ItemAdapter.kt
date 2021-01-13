package com.example.recyclerproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerproject.Data.Item

class ItemAdapter (private val onClick: (Item) -> Unit
) :
        ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback) {

    class ItemViewHolder(itemView: View,
                         val onClick: (Item) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        private val itemIdTextView: TextView = itemView.findViewById(R.id.item_id)
        private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val itemDeleteButton: Button = itemView.findViewById(R.id.delete_button)
        private var currentItem: Item? = null

        init {
            itemDeleteButton.setOnClickListener {
                if (currentItem != null) {
                    currentItem?.let{
                        onClick(it)
                    }
                }
            }
        }

        fun bind(item: Item) {
            currentItem = item
            itemIdTextView.text = item.id.toString()
            itemNameTextView.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ItemViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
