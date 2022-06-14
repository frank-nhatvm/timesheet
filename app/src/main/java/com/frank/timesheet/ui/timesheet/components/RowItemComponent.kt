package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.data.entities.Row
import com.frank.timesheet.data.entities.RowTimeSheet

class RowItemComponent constructor(
    context: Context,
    private val row: RowTimeSheet,
    private val rowItemCallback: RowItemCallback
) : BaseComponent(context) {

    private var tvRowName: TextView? = null
    private var doneTaskView: View? = null
    private var isSelected = false

    override fun createView(): View {
        isSelected = row.isSelected

        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_row_item, null, false)

        doneTaskView = rootView.findViewById(R.id.doneTaskView)

        tvRowName = rootView.findViewById(R.id.tvRowName)
        tvRowName?.text = row.row.rowName

        rootView.setOnClickListener {
            isSelected = !isSelected
            updateView()
            rowItemCallback.selectRow(row, isSelected)
        }

        updateView()

        return rootView
    }

    private fun updateView() {
        doneTaskView?.visibility = View.GONE
        if (isSelected) {
            tvRowName?.setTextColor(R.color.white)
            rootView.setBackgroundResource(R.drawable.background_row_item_selected)
            if (row.hasDoneTask()) {
                doneTaskView?.visibility = View.VISIBLE
            }
        } else {
            tvRowName?.setTextColor(R.color.text_color)
            rootView.setBackgroundResource(R.drawable.background_row_item)
        }
    }

    interface RowItemCallback {
        fun selectRow(rowTimeSheet: RowTimeSheet, isSelected: Boolean)
    }

}