package com.example.todolist.fragment


import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolist.OnItemClickListener
import com.example.todolist.SwipeController
import com.example.todolist.adapter.ToDoListAdapter
import com.example.todolist.database.AppDB
import com.example.todolist.database.ToDoDao
import com.example.todolist.databinding.FragmentToDoListBinding
import com.example.todolist.itemmanager.ToDoItemManager
import com.example.todolist.model.ToDo
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class ToDoListFragment : Fragment() {

    private lateinit var database: ToDoDao
    private lateinit var toDoList: LiveData<List<ToDo>>
    private lateinit var adapter: ToDoListAdapter
    private var toDoJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + toDoJob)
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentToDoListBinding.inflate(inflater)

        application = requireNotNull(this.activity).application

        uiScope.launch {
            withContext(Dispatchers.IO) {
                database = AppDB.getInstance(application).toDoDao
                toDoList = database.loadToDoList()
            }

            val listener = object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(activity, "Just click ${position}", Toast.LENGTH_LONG).show()
                }

                override fun onCheckBoxClick(position: Int) {
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            toDoList.value?.get(position)?.toDoId?.let {
                                var todo = database.getTodo(it)
                                todo!!.toDoStatus = true
                                database.updateToDo(todo)
                            }
                        }
                    }
                }

                override fun swipeRight(position: Int) {
                    val dialog = ToDoItemManager()
                    var args = Bundle()
                    args.putLong("id", toDoList.value!![position]!!.toDoId)
                    dialog.arguments = args
                    dialog.show(fragmentManager, "edit todo")
                }

                override fun swipeLeft(position: Int) {
                    uiScope.launch {
                        withContext(Dispatchers.IO) {
                            toDoList.value?.get(position)?.toDoId?.let {
                                val todo = database.getTodo(it)
                                database.deleteTodo(todo!!)
                            }
                        }
                    }
                }
            }

            //set adapter
            adapter = ToDoListAdapter(listener)

            binding.toDoList.adapter = adapter

            var itemTouchHelper = ItemTouchHelper(SwipeController(listener, application))
            itemTouchHelper.attachToRecyclerView(binding.toDoList)

            toDoList.observe(this@ToDoListFragment, Observer {
                it.let {
                    adapter.submitList(it)
                }
            })
        }

        return binding.root
    }


}
