package com.example.todolist.itemmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todolist.database.AppDB
import com.example.todolist.database.ToDoDao
import com.example.todolist.databinding.DialogAddToDoBinding
import com.example.todolist.model.ToDo
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ToDoItemManager: DialogFragment() {

    private var database: ToDoDao? = null
    private var dateDb: LocalDate? = null
    private var timeDb: LocalTime? = null
    private var todo: ToDo? = null
    private var isEdit = false
    private var hasDateTime = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogAddToDoBinding.inflate(inflater)

        val cal = Calendar.getInstance()
        val application = requireNotNull(this.activity).application

        database = AppDB.getInstance(application).toDoDao

        arguments?.getLong("id")?.let {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    todo = database!!.getTodo(it!!)
                }
                binding.etToDo.setText(todo!!.toDoTitle)
                todo!!.toDoDate?.let {
                    hasDateTime = true
                    dateDb = it
                    binding.tvDate.text = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    timeDb = todo!!.toDoTime
                    binding.tvTime.text = todo!!.toDoTime!!.format(DateTimeFormatter.ofPattern("hh:mm"))
                }
            }
            isEdit = true
        }



        binding.ibDate.setOnClickListener {

            var y = cal.get(Calendar.YEAR)
            var m = cal.get(Calendar.MONTH)
            var d = cal.get(Calendar.DAY_OF_MONTH)

            if (hasDateTime) {
                y = dateDb!!.year
                m = dateDb!!.monthValue - 1
                d = dateDb!!.dayOfMonth
            }

            var dialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener
            { _, year, month, dayOfMonth ->
                val correctMonth = month + 1
                val date = "$dayOfMonth/$correctMonth/$year"
                binding.tvDate.text = date
                this.dateDb = LocalDate.of(year, correctMonth, dayOfMonth)
            }
                , y, m, d)
            dialog.show()
        }

        binding.ibTime.setOnClickListener {
            var h = cal.get(Calendar.HOUR_OF_DAY)
            var m = cal.get(Calendar.MINUTE)

            if (hasDateTime) {
                h = timeDb!!.hour
                m = timeDb!!.minute
            }

            var dialog = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener
            { _, hourOfDay, minute ->
                binding.tvTime.text = "$hourOfDay:$minute"
                this.timeDb = LocalTime.of(hourOfDay, minute)
            }
                , h, m, true)
            dialog.show()
        }

        binding.btnToDoCancel.setOnClickListener { dismiss() }

        binding.btnToDoConfirm.setOnClickListener {
            if (binding.etToDo.text.isEmpty()) {
                Toast.makeText(activity, "Please enter Title", Toast.LENGTH_SHORT).show()
            } else {
                val text = binding.etToDo.text.toString()
                if (dateDb != null && timeDb != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            if (isEdit) {
                                todo!!.toDoTitle = text
                                todo!!.toDoDate = dateDb
                                todo!!.toDoTime = timeDb
                                database!!.updateToDo(todo!!)
                            } else {
                                database!!.insertToDo(ToDo(0, text, dateDb, timeDb))
                            }
                        }
                        dismiss()
                    }
                } else if (dateDb != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO + NonCancellable) {
                            if (isEdit) {
                                todo!!.toDoTitle = text
                                todo!!.toDoDate = dateDb
                                todo!!.toDoTime = LocalTime.of(0, 0)
                                database!!.updateToDo(todo!!)
                            } else {
                                database!!.insertToDo(ToDo(0, text, dateDb, LocalTime.of(0, 0)))
                            }
                        }
                        dismiss()
                    }
                } else if (timeDb != null) {
                    Toast.makeText(activity, "Please enter Date", Toast.LENGTH_SHORT).show()
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO + NonCancellable) {
                            if (isEdit) {
                                todo!!.toDoTitle = text
                                todo!!.toDoDate = null
                                todo!!.toDoTime = null
                                database!!.updateToDo(todo!!)
                            } else {
                                database!!.insertToDo(ToDo(0, text))
                            }
                        }
                        dismiss()
                    }
                }
            }
        }

        return binding.root
    }

}