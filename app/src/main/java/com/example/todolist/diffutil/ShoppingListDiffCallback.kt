package com.example.todolist.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.model.Shopping

class ShoppingListDiffCallback: DiffUtil.ItemCallback<Shopping>() {
    override fun areItemsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
        return oldItem.shoppingId == newItem.shoppingId
    }

    override fun areContentsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
        return oldItem == newItem
    }
}