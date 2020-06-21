package com.example.todolist.fragment


import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.todolist.itemmanager.ShoppingItemManager
import com.example.todolist.itemmanager.ToDoItemManager
import com.example.todolist.R
import com.example.todolist.adapter.SectionPageAdapter
import com.example.todolist.databinding.FragmentBaseBinding

/**
 * A simple [Fragment] subclass.
 */
class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentBaseBinding.inflate(inflater)

        var sectionAdapter =
            SectionPageAdapter(childFragmentManager)


        binding.vpBase.adapter = sectionAdapter
        binding.vpBase.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding.baseImage.setImageResource(R.drawable.ic_to_do)
                } else {
                    binding.baseImage.setImageResource(R.drawable.ic_shopping)
                }
            }

        })
        binding.tlBase.setupWithViewPager(binding.vpBase)
        activity?.setActionBar(binding.toolbar)
        binding.toolbar.overflowIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_add_white_24dp)
        binding.toolbar.inflateMenu(R.menu.add_menu)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_shopping) {
            //Toast.makeText(activity, "add shopping", Toast.LENGTH_LONG).show()
            //this.findNavController().navigate(R.id.action_baseFragment_to_addShoppingList2)
            val dialogFragment = ShoppingItemManager()
            dialogFragment.show(fragmentManager, "add shopping")
            return true
        } else if (item?.itemId == R.id.add_to_do) {
            val dialogFragment = ToDoItemManager()
            dialogFragment.show(fragmentManager, "add to do")
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
