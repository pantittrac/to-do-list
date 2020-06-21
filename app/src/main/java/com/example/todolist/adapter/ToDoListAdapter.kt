package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.itemevent.OnItemClickListener
import com.example.todolist.R
import com.example.todolist.diffutil.ToDoListDiffCallback
import com.example.todolist.model.ToDo
import java.time.format.DateTimeFormatter

class ToDoListAdapter(private val listener: OnItemClickListener): ListAdapter<ToDo, ToDoListAdapter.ViewHolder>(ToDoListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {

        private val checkBox: CheckBox = itemView.findViewById(R.id.to_do_item_checkbox)
        private val title: TextView = itemView.findViewById(R.id.to_do_item_title)
        private val dateTime: TextView = itemView.findViewById(R.id.to_do_item_datetime)

        init {
            checkBox.setOnClickListener { listener.onCheckBoxClick(adapterPosition) }
        }

        fun bind(toDo: ToDo) {
            title.text = toDo.toDoTitle
            if (toDo.toDoDate != null) {
                dateTime.text = "${toDo.toDoTime}   ${(toDo.toDoDate)!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}"
            } else {
                dateTime.text = ""
            }
            if (toDo.toDoStatus) {
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
                val view = layoutInflater.inflate(R.layout.to_do_item, parent, false)
                return ViewHolder(view, listener)
            }
        }
    }

}