package com.example.todolist.fragment


import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolist.itemevent.OnItemClickListener
import com.example.todolist.itemevent.SwipeController
import com.example.todolist.adapter.ShoppingListAdapter
import com.example.todolist.database.AppDB
import com.example.todolist.database.ShoppingDao
import com.example.todolist.databinding.FragmentShoppingListBinding
import com.example.todolist.itemmanager.ShoppingItemManager
import com.example.todolist.model.Shopping
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListFragment : Fragment() {

    private lateinit var database: ShoppingDao
    private lateinit var shoppingList: LiveData<List<Shopping>>
    private lateinit var adapter: ShoppingListAdapter
    private var shoppingJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + shoppingJob)
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentShoppingListBinding.inflate(inflater)

        application = requireNotNull(this.activity).application

        uiScope.launch {
            withContext(Dispatchers.IO) {
                database = AppDB.getInstance(application).shoppingDao
                shoppingList = database.loadShoppingList()
            }

            val listener = object :
                OnItemClickListener {

                override fun onCheckBoxClick(position: Int) {
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            shoppingList.value?.get(position)?.shoppingId?.let {
                                var shopping = database.getShopping(it)
                                shopping!!.shoppingStatus = true
                                database.updateShopping(shopping)
                            }
                        }
                    }
                }

                override fun swipeRight(position: Int) {
                    val dialog = ShoppingItemManager()
                    var args = Bundle()
                    args.putLong("id", shoppingList.value!![position]!!.shoppingId)
                    dialog.arguments = args
                    dialog.show(fragmentManager, "edit shopping")
                }

                override fun swipeLeft(position: Int) {
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            shoppingList.value?.get(position)?.shoppingId?.let {
                                val shopping = database.getShopping(it)
                                database.deleteShopping(shopping!!)
                            }
                        }
                    }
                }
            }

            adapter = ShoppingListAdapter(listener)

            binding.shoppingItem.adapter = adapter

            var itemTouchHelper = ItemTouchHelper(
                SwipeController(
                    listener,
                    application
                )
            )
            itemTouchHelper.attachToRecyclerView(binding.shoppingItem)

            shoppingList.observe(this@ShoppingListFragment, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }
        return binding.root
    }

    // change later
    override fun onDestroy() {
        super.onDestroy()
        shoppingJob.cancel()
    }
}
