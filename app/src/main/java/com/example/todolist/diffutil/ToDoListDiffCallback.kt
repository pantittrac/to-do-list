package com.example.todolist.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.model.ToDo

class ToDoListDiffCallback: DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.toDoId == newItem.toDoId
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }
}