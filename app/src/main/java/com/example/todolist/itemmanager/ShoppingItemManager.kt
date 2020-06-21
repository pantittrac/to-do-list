package com.example.todolist.itemmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todolist.database.AppDB
import com.example.todolist.databinding.DialogAddShoppingBinding
import com.example.todolist.model.Shopping
import kotlinx.coroutines.*

class ShoppingItemManager: DialogFragment() {

    private var shopping: Shopping? = null
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogAddShoppingBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val database = AppDB.getInstance(application).shoppingDao

        arguments?.getLong("id")?.let {
            isEdit = true
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    shopping = database.getShopping(it)
                }
                binding.etShopping.setText(shopping!!.shoppingTitle)
            }
        }

        binding.btnShoppingCancel.setOnClickListener {
            dismiss()
        }

        binding.btnShoppingConfirm.setOnClickListener {
            if (binding.etShopping.text.isEmpty()) {
                Toast.makeText(activity, "Please enter Title", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO + NonCancellable) {
                        val text = binding.etShopping.text.toString()
                        if (isEdit) {
                            shopping!!.shoppingTitle = text
                            database.updateShopping(shopping!!)
                        } else {
                            database.insertShopping(Shopping(0, text))
                        }
                    }
                    dismiss()
                }
            }
        }

        return binding.root
    }
}