package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.ToDo
import java.time.LocalDate
import java.time.LocalTime

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

    @Query("SELECT * FROM to_do_list ORDER BY to_do_status, to_do_date IS NULL, to_do_date, to_do_time")
    fun loadToDoList(): LiveData<List<ToDo>>

    @Query("SELECT * FROM to_do_list WHERE to_do_date = :toDoDate AND to_do_time = :toDoTime")
    fun loadTodoByDateTime(toDoDate: LocalDate, toDoTime: LocalTime): List<ToDo>
}