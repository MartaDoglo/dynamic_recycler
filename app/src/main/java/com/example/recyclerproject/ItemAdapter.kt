package com.example.recyclerproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerproject.Data.Item


class ItemAdapter(private val onClick: (Item, Int) -> Unit
) :
        ListAdapter<Item, ItemAdapter.ItemViewHolder>(
                ItemDiffCallback
        ) {

    class ItemViewHolder(itemView: View,
                         val onClick: (Item, Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
            private val itemIdTextView: TextView = itemView.findViewById(R.id.item_id)
            private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
            private val itemDeleteButton: Button = itemView.findViewById(R.id.delete_button)
            private val itemContainer: LinearLayoutCompat = itemView.findViewById(R.id.item_container)
            private var currentItem: Item? = null

        init {
                itemDeleteButton.setOnClickListener {
                    if (currentItem != null) {
                        currentItem?.let{
                            onClick(it, layoutPosition)
                        }
                    }
                }
            }

            fun bind(item: Item) {
               itemContainer.animation = AnimationUtils.loadAnimation(itemNameTextView.context, R.anim.del_item)
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
