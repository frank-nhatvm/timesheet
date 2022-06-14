package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.common.Utils
import com.frank.timesheet.data.entities.RowTimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.ui.timesheet.TimeSheetViewModel

class RowComponent constructor(
    context: Context,
    private val listRowTimeSheet: List<RowTimeSheet>,
    private val timeSheetId: Int,
    private val typeJob: TypeJob,
    private val timeSheetViewModel: TimeSheetViewModel
) :
    BaseComponent(context) {

    private val listRowItemComponent = mutableListOf<RowItemComponent>()
    private val listRowValueItemComponent = mutableListOf<RowItemValueComponent>()

    private var llRowItemComponent: LinearLayout? = null
    private var llRowItemValueComponent: LinearLayout? = null


    override fun createView(): View {

        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_row, null, false)

        llRowItemComponent = rootView.findViewById(R.id.llRowItemComponent)

        llRowItemValueComponent = rootView.findViewById(R.id.llRowItemValueComponent)

        initRowItemComponent()

        return rootView
    }

    private fun initRowItemComponent() {

        val rowItemCallback = object : RowItemComponent.RowItemCallback {
            override fun selectRow(rowTimeSheet: RowTimeSheet, isSelected: Boolean) {
                timeSheetViewModel.toggleRow(
                    timeSheetId = timeSheetId,
                    rowId = rowTimeSheet.row.rowId,
                    isSelected = isSelected,
                    typeJob = typeJob
                )
                if (isSelected) {
                    addRowItemValueComponent(rowTimeSheet)
                } else {
                    removeRowItemValueComponent(rowTimeSheet)
                }
            }
        }

        listRowTimeSheet.forEach { rowTimeSheet ->
            val component = RowItemComponent(
                context = context,
                row = rowTimeSheet,
                rowItemCallback = rowItemCallback
            )
            listRowItemComponent.add(component)
            llRowItemComponent?.addView(component.createView(), createSpaceHorizontal())

            if(rowTimeSheet.isSelected){
                addRowItemValueComponent(rowTimeSheet)
            }
        }

    }

    private fun addRowItemValueComponent(rowTimeSheet: RowTimeSheet) {
        val component = RowItemValueComponent(context = context, row = rowTimeSheet,timeSheetId = timeSheetId,typeJob = typeJob,timeSheetViewModel = timeSheetViewModel)
        listRowValueItemComponent.add(component)
        llRowItemValueComponent?.addView(component.createView(), createSpaceVertical())
    }

    private fun removeRowItemValueComponent(rowTimeSheet: RowTimeSheet) {
        var removeIndex = -1
        listRowValueItemComponent.forEachIndexed{ index, rowValueComponent ->
            if (rowTimeSheet.row.rowId == rowValueComponent.getRowId()) {
                removeIndex = index
                llRowItemValueComponent?.removeView(rowValueComponent.getRootViewForRemove())
            }
        }
        if(removeIndex != -1){
            listRowValueItemComponent.removeAt(removeIndex)
        }
    }


    private fun createSpaceVertical(): LinearLayout.LayoutParams {
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.topMargin = Utils.dpToPixel(12f, context)
        return param
    }

    private fun createSpaceHorizontal(): LinearLayout.LayoutParams {
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.marginEnd = Utils.dpToPixel(12f, context)
        return param
    }


}