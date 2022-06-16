package com.frank.timesheet.ui.timesheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.frank.timesheet.R
import com.frank.timesheet.common.EventObserver
import com.frank.timesheet.common.Utils
import com.frank.timesheet.databinding.FragmentTimesheetBinding
import com.frank.timesheet.ui.timesheet.components.TimeSheetComponent
import com.google.android.material.tabs.TabLayoutMediator


class TimesheetFragment : Fragment() {


    private val viewModel by viewModels<TimeSheetViewModel>()
    private lateinit var binding: FragmentTimesheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimesheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TimeSheetFragmentAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.setText(getTabTitleByPosition(position))
        }.attach()
    }

    private fun getTabTitleByPosition(position: Int): Int {
        return when (position) {
            1 -> R.string.thinning
            else -> R.string.pruning
        }
    }

    class TimeSheetFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            val fragment = TimeSheetChildFragment()
            fragment.arguments = Bundle().apply {
                putInt(TimeSheetChildFragment.TYPE_PAGE, position)
            }
            return fragment
        }
    }


}