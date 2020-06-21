package com.example.todolist.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.Converters
import com.example.todolist.model.Shopping
import com.example.todolist.model.ToDo

@Database(entities = [ToDo::class, Shopping::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDB: RoomDatabase() {
    abstract val toDoDao: ToDoDao
    abstract val shoppingDao: ShoppingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDB::class.java,
                        "to_do_list_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance!!
            }
        }
    }
}