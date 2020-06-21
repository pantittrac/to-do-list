package com.example.todolist

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun toDate(txt: String?): LocalDate? {
        //return LocalDate.parse(txt) ?: null
        return if (txt.isNullOrEmpty()) null
        else LocalDate.parse(txt)
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString() ?: null
    }

    @TypeConverter
    fun toTime(txt: String?): LocalTime? {
        //return LocalTime.parse(txt) ?: null
        return if (txt.isNullOrEmpty()) null
        else LocalTime.parse(txt)
    }

    @TypeConverter
    fun toTimeString(time: LocalTime?): String? {
        return time?.toString() ?: null
    }
}