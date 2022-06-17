package com.frank.timesheet.ui.timesheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frank.timesheet.R
import com.frank.timesheet.common.EventObserver
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.databinding.FragmentTimesheetBinding
import com.frank.timesheet.databinding.FragmentTimesheetChildBinding

class TimeSheetChildFragment : Fragment() {
    companion object {
        const val TYPE_PAGE = "type_page"
    }

    private val viewModel by viewModels<TimeSheetViewModel>({ requireParentFragment() })

    private lateinit var binding: FragmentTimesheetChildBinding
    private lateinit var typeJob: TypeJob
    private var adapter: TimeSheetAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        typeJob = if (arguments?.getInt(TYPE_PAGE) == 1) TypeJob.THINNING else TypeJob.PRUNING

        binding = FragmentTimesheetChildBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rcvStaffTimeSheet.layoutManager = layoutManager

        AppCompatResources.getDrawable(requireContext(), R.drawable.divider_staff_timesheet)
            ?.let { drawable ->
                val divider =
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                divider.setDrawable(drawable)
                binding.rcvStaffTimeSheet.addItemDecoration(divider)
            }

        adapter = TimeSheetAdapter(timeSheetViewModel = viewModel)
        binding.rcvStaffTimeSheet.adapter = adapter

        if (typeJob == TypeJob.PRUNING) {
            viewModel.listPruningTimeSheet.observe(viewLifecycleOwner) { list ->
                adapter?.submitList(list)
            }
            viewModel.pruningTimeSheetAddMaxTree.observe(
                viewLifecycleOwner,
                EventObserver { isAdded ->
                    if (isAdded) {
                        val itemCount = viewModel.listPruningTimeSheet.value?.size ?: 1
                        updateAllData(itemCount)
                    }
                })
            viewModel.pruningTimeSheetApplyPieceRate.observe(viewLifecycleOwner,EventObserver{
                isApply->
                if(isApply){
                    val itemCount = viewModel.listPruningTimeSheet.value?.size ?: 1
                    updateAllData(itemCount)
                }
            })
        } else {
            viewModel.listThinningTimeSheet.observe(viewLifecycleOwner) { list ->
                adapter?.submitList(list)
            }

            viewModel.thinningTimeSheetAddMaxTree.observe(
                viewLifecycleOwner,
                EventObserver { isAdded ->
                    if (isAdded) {
                        val itemCount = viewModel.listThinningTimeSheet.value?.size ?: 1
                        updateAllData(itemCount)
                    }
                })
            viewModel.thinningTimeSheetApplyPieceRate.observe(viewLifecycleOwner,EventObserver{
                isApply->
                if(isApply){
                    val itemCount = viewModel.listThinningTimeSheet.value?.size ?: 1
                    updateAllData(itemCount)
                }
            })
        }


        binding.btnConfirm.setOnClickListener {

        }

        binding.btnAddMaxTree.setOnClickListener {
            viewModel.addMaxTree(typeJob)
        }

    }

    private fun updateAllData(itemCount: Int) {
        adapter?.notifyItemRangeChanged(0, itemCount)
    }

}