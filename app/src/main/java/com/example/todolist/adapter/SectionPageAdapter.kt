package com.example.todolist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.todolist.fragment.ShoppingListFragment
import com.example.todolist.fragment.ToDoListFragment

class SectionPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ToDoListFragment()
            1 -> return ShoppingListFragment()
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "To Do List"
            1 -> return "Shopping List"

        }
        return null
    }
}