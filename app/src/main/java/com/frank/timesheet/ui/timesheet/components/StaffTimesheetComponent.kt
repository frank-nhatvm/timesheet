package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.databinding.ComponentTimesheetBinding
import kotlin.random.Random

class StaffTimesheetComponent constructor(context: Context, private val timeSheet: TimeSheet) :
    BaseComponent(context) {

    private lateinit var dataBinding: ComponentTimesheetBinding
    private var currentPieceRateValue = ""
    private val listAvatarBackgroundColor = listOf("#cc6699","#993366","#993333","#800000","#993300")

    override fun createView(): View {
        val inflater = LayoutInflater.from(context)
        dataBinding = ComponentTimesheetBinding.inflate(inflater)

        rootView = dataBinding.root
        return rootView
    }

    fun updatePieceRateValue(newRate: String){
        currentPieceRateValue = newRate

    }

    private fun generateAvatarBackgroundColor():Int{
        val index = Random.nextInt(0,listAvatarBackgroundColor.size)
        return Color.parseColor(listAvatarBackgroundColor[index])
    }

    interface TimeSheetCallback {
        fun changePieceRate(value: String, isApplyToAll: Boolean)
    }

}