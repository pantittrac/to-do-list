package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.itemevent.OnItemClickListener
import com.example.todolist.R
import com.example.todolist.diffutil.ShoppingListDiffCallback
import com.example.todolist.model.Shopping

class ShoppingListAdapter(private var listener: OnItemClickListener): ListAdapter<Shopping, ShoppingListAdapter.ViewHolder>(ShoppingListDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(
            parent,
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.shopping_item_title)
        private val checkBox: CheckBox = itemView.findViewById(R.id.shopping_item_checkbox)

        init {
            checkBox.setOnClickListener { listener.onCheckBoxClick(adapterPosition) }
        }

        fun bind(shopping: Shopping) {
            title.text = shopping.shoppingTitle
            if (shopping.shoppingStatus) {
                checkBox.isChecked = true
                checkBox.isEnabled = false
            } else {
                checkBox.isChecked = false
                checkBox.isEnabled = true
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: OnItemClickListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.shopping_item, parent, false)
                return ViewHolder(
                    view,
                    listener
                )
            }
        }

    }
}