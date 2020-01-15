package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {
    @Insert
    fun insertToDo(todo: ToDo)

    @Update
    fun updateToDo(todo: ToDo)

    @Delete
    fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM to_do_list WHERE to_do_id = :toDoId")
    fun getTodo(toDoId: Long): ToDo?

    @Query("SELECT * FROM to_do_list")
    fun loadToDoList(): LiveData<List<ToDo>>
}