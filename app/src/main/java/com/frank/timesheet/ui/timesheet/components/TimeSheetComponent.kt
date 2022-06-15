package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.common.Utils
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.ui.timesheet.TimeSheetViewModel

class TimeSheetComponent constructor(
    context: Context,
    private val timeSheets: List<TimeSheet>,
    private val timeSheetViewModel: TimeSheetViewModel
) : BaseComponent(context = context) {

    private var typeJob: TypeJob = TypeJob.PRUNING
    private val listStaffTimesheetComponent = mutableListOf<StaffTimesheetComponent>()

    override fun createView(): View {
        typeJob = getTypeJob()

        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_timesheet, null, false)

        val tvJobTitle = rootView.findViewById<TextView>(R.id.tvJobTitle)
        tvJobTitle.setText(getJobTitle())

        val btnAddMaxTree = rootView.findViewById<TextView>(R.id.btnAddMaxTree)
        btnAddMaxTree.setOnClickListener {
            timeSheetViewModel.addMaxTree(typeJob)
        }

        val llTimeSheet = rootView.findViewById<LinearLayout>(R.id.llStaffTimeSheets)
        timeSheets.forEachIndexed { index, timeSheet ->
            val component = StaffTimesheetComponent(context, timeSheet,timeSheetViewModel)
            listStaffTimesheetComponent.add(component)
            llTimeSheet.addView(component.createView())
            if (index != timeSheets.lastIndex) {
                val divider = View(context)
                divider.setBackgroundColor(R.color.text_color_subtitle)
                llTimeSheet.addView(divider, createDividerLayoutParam())
            }
        }

        return rootView
    }

    private fun getTypeJob(): TypeJob{
        val firstTimeSheet = timeSheets.firstOrNull()
        return if (firstTimeSheet?.typeJob == TypeJob.PRUNING) TypeJob.PRUNING else TypeJob.THINNING
    }

    private fun getJobTitle(): Int {
        val firstTimeSheet = timeSheets.firstOrNull()
        return if (firstTimeSheet?.typeJob == TypeJob.PRUNING) R.string.pruning else R.string.thinning
    }

    private fun createDividerLayoutParam(): LinearLayout.LayoutParams {
        val height = Utils.dpToPixel(1f, context)
        val margin = Utils.dpToPixel(24f, context)

        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        param.topMargin = margin
        param.bottomMargin = margin

        return param
    }

    fun addMaxTree(timeSheets: List<TimeSheet>){
        listStaffTimesheetComponent.forEach { component ->
            val componentId = component.getTimeSheetId()
            timeSheets.find { it.timeSheetId == componentId }?.let {
                timeSheet ->
                component.addMaxTree(timeSheet.block.listRowTimeSheet)
            }
        }

    }

}