package com.example.todolist.itemevent

interface OnItemClickListener {
    fun onCheckBoxClick(position: Int)
    fun swipeRight(position: Int)
    fun swipeLeft(position: Int)
}