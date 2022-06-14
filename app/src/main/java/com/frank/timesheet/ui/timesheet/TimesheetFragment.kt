package com.frank.timesheet.ui.timesheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.frank.timesheet.common.Utils
import com.frank.timesheet.databinding.FragmentTimesheetBinding
import com.frank.timesheet.ui.timesheet.components.TimeSheetComponent


class TimesheetFragment : Fragment() {


    private val viewModel by viewModels<TimeSheetViewModel>()
    private lateinit var binding: FragmentTimesheetBinding
    private var isRenderPruningTimeSheet = false
    private var isRenderThinningTimeSheet = false

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
        viewModel.listPruningTimeSheet.observe(viewLifecycleOwner) { list ->
            if (!isRenderPruningTimeSheet) {
                isRenderPruningTimeSheet = true
                val pruningTimeSheetComponent =
                    TimeSheetComponent(
                        context = requireContext(),
                        timeSheets = list,
                        timeSheetViewModel = viewModel
                    )
                binding.llTimeSheet.addView(pruningTimeSheetComponent.createView(), generateParam())
            }
        }

        viewModel.listThinningTimeSheet.observe(viewLifecycleOwner) { list ->
            if (!isRenderThinningTimeSheet) {
                isRenderThinningTimeSheet = true
                val thinningTimeSheetComponent =
                    TimeSheetComponent(
                        context = requireContext(),
                        timeSheets = list,
                        timeSheetViewModel = viewModel
                    )
                binding.llTimeSheet.addView(
                    thinningTimeSheetComponent.createView(),
                    generateParam()
                )
            }
        }
    }

    private fun generateParam(): LinearLayout.LayoutParams {
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.topMargin = Utils.dpToPixel(24f, requireContext())
        return param
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRenderPruningTimeSheet = false
        isRenderThinningTimeSheet = false
    }

}