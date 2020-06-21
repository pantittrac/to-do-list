package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.Shopping

@Dao
interface ShoppingDao {

    @Insert
    fun insertShopping(shopping: Shopping)

    @Update
    fun updateShopping(shopping: Shopping)

    @Delete
    fun deleteShopping(shopping: Shopping)

    @Query("SELECT * FROM shopping_list WHERE shopping_id = :shoppingId")
    fun getShopping(shoppingId: Long): Shopping?

    @Query("SELECT * FROM shopping_list ORDER BY shopping_status")
    fun loadShoppingList(): LiveData<List<Shopping>>
}