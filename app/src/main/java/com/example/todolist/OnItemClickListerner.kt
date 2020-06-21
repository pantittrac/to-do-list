package com.example.todolist

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onCheckBoxClick(position: Int)
    fun swipeRight(position: Int)
    fun swipeLeft(position: Int)
}