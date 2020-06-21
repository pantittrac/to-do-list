package com.example.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class Shopping(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shopping_id")
    var shoppingId: Long = 0L,

    @ColumnInfo(name = "shopping_title")
    var shoppingTitle: String,

    @ColumnInfo(name = "shopping_status")
    var shoppingStatus: Boolean = false
)