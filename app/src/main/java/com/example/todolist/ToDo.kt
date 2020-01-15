package com.example.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "to_do_list")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "to_do_id")
    var toDoId: Long = 0L,

    @ColumnInfo(name = "to_do_title")
    var toDoTitle: String,

    @ColumnInfo(name = "to_do_time")
    var toDoTime: LocalDateTime ?= null,

    @ColumnInfo(name = "to_do_status")
    var toDoStatus: Boolean = false
)