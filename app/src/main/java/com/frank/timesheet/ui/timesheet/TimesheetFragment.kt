package com.frank.timesheet.ui.timesheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.frank.timesheet.databinding.FragmentTimesheetBinding


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
        viewModel.listTimesheet.observe(viewLifecycleOwner){
            list ->

        }
    }

}