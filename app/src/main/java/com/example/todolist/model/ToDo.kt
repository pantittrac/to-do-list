package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "to_do_list")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "to_do_id")
    var toDoId: Long = 0L,

    @ColumnInfo(name = "to_do_title")
    var toDoTitle: String,

    @ColumnInfo(name = "to_do_date")
    var toDoDate: LocalDate ?= null, //change to date later

    @ColumnInfo(name = "to_do_time")
    var toDoTime: LocalTime ?= null, //change to time later

    @ColumnInfo(name = "to_do_status")
    var toDoStatus: Boolean = false
)