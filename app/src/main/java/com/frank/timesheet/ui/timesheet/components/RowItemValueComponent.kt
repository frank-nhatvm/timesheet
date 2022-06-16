package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.common.Utils
import com.frank.timesheet.data.entities.RowTimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.ui.timesheet.TimeSheetViewModel
import kotlin.Exception

class RowItemValueComponent constructor(
    context: Context, private val row: RowTimeSheet, private val timeSheetId: Int,
    private val typeJob: TypeJob,
    private val timeSheetViewModel: TimeSheetViewModel
) :
    BaseComponent(context) {

    private var edtTreeNumber: EditText? = null

    override fun createView(): View {
        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_row_item_value, null, false)

        val tvRowValueTitle = rootView.findViewById<TextView>(R.id.tvRowValueTitle)
        tvRowValueTitle.text = context.resources.getString(R.string.tree_for_row, row.row.rowName)

        val tvTotalTreeNumber = rootView.findViewById<TextView>(R.id.tvTotalTreeNumber)
        val maxTree = row.row.maxTreeNumber.toString()
        tvTotalTreeNumber.text = "/$maxTree"

        rootView.setOnClickListener {
            edtTreeNumber?.requestFocus()
            val length = edtTreeNumber?.length() ?: 0
            if(length > 0) {
                edtTreeNumber?.setSelection(length)
            }
            Utils.showSoftKeyboard(edtTreeNumber,context)
        }

        edtTreeNumber = rootView.findViewById(R.id.edtTreeNumber)

        edtTreeNumber?.setText(row.treeOfCurrentCustomer.toString())

        edtTreeNumber?.addTextChangedListener { text ->
            try {
                val number = text.toString().toInt()
                timeSheetViewModel.updateTreeNumberForRow(
                    treeNumber = number,
                    rowId = row.row.rowId,
                    timeSheetId = timeSheetId,
                    typeJob = typeJob
                )
            } catch (e: Exception) {

            }
        }

        val tvJobs = rootView.findViewById<TextView>(R.id.tvJobs)
        tvJobs.text = row.getListJobInString()

        return rootView
    }


    fun updateTreeNumber(number: Int) {
        edtTreeNumber?.setText(number.toString())
    }

    fun getRowId(): Int {
        return row.row.rowId
    }

    fun getRootViewForRemove(): View {
        return rootView
    }
}