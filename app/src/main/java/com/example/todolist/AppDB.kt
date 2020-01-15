package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract val toDoModel: ToDoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    Room.databaseBuilder(context.applicationContext,
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