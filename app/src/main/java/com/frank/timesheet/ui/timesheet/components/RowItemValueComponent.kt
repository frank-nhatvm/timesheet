package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.data.entities.RowTimeSheet
import java.lang.Exception

class RowItemValueComponent constructor(context: Context, private val row: RowTimeSheet) :
    BaseComponent(context) {

    private var edtTreeNumber: EditText? = null

    override fun createView(): View {
        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_row_item_value, null, false)

        val tvRowValueTitle = rootView.findViewById<TextView>(R.id.tvRowValueTitle)
        tvRowValueTitle.text = context.resources.getString(R.string.tree_for_row, row.row.rowName)

        val tvTotalTreeNumber = rootView.findViewById<TextView>(R.id.tvTotalTreeNumber)
        tvTotalTreeNumber.text = row.row.maxTreeNumber.toString()

        edtTreeNumber = rootView.findViewById(R.id.edtTreeNumber)
        edtTreeNumber?.setText(row.treeOfCurrentCustomer.toString())

        val tvJobs = rootView.findViewById<TextView>(R.id.tvJobs)
        tvJobs.text = row.getListJobInString()

        return rootView
    }


    fun getDoneTreeNumber(): Int {
        val number = edtTreeNumber?.text.toString()

        return try {
            number.toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun updateTreeNumber(number: Int) {
        edtTreeNumber?.setText( number.toString())
    }

}