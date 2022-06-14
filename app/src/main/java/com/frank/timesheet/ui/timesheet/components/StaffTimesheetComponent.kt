package com.frank.timesheet.ui.timesheet.components

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import com.frank.timesheet.R
import com.frank.timesheet.base.component.BaseComponent
import com.frank.timesheet.data.entities.RateType
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.databinding.ComponentStaffTimesheetBinding
import com.frank.timesheet.ui.timesheet.TimeSheetViewModel

import kotlin.random.Random

class StaffTimesheetComponent constructor(
    context: Context, private val timeSheet: TimeSheet,
    private val timeSheetViewModel: TimeSheetViewModel
) :
    BaseComponent(context) {


    private val listAvatarBackgroundColor =
        listOf("#cc6699", "#993366", "#993333", "#800000", "#993300")
    private var currentRateType = RateType.PIECE_RATE

    private var tvStaffAvatar: TextView? = null
    private var btnPieceRate: AppCompatButton? = null
    private var btnWages: AppCompatButton? = null
    private var llRows: LinearLayout? = null
    private var layoutPieceRateValue: ConstraintLayout? = null
    private var tvWagesDescription: TextView? = null
    private var tvStaffName: TextView? = null
    private var tvOrchardName: TextView? = null
    private var tvBlockName: TextView? = null
    private var edtPieceRate: EditText? = null

    override fun createView(): View {
        val inflater = LayoutInflater.from(context)
        rootView = inflater.inflate(R.layout.component_staff_timesheet, null, false)

        tvStaffAvatar = rootView.findViewById(R.id.tvStaffAvatar)
        btnPieceRate = rootView.findViewById(R.id.btnPieceRate)
        btnWages = rootView.findViewById(R.id.btnWages)
        tvWagesDescription = rootView.findViewById(R.id.tvWagesDescription)
        llRows = rootView.findViewById(R.id.llRows)
        layoutPieceRateValue = rootView.findViewById(R.id.layoutPieceRateValue)
        tvStaffName = rootView.findViewById(R.id.tvStaffName)
        tvStaffName?.text = timeSheet.staff.fullName()
        edtPieceRate = rootView.findViewById(R.id.edtPieceRate)

        tvOrchardName = rootView.findViewById(R.id.tvOrchardName)
        tvOrchardName?.text = timeSheet.orchardName

        tvBlockName = rootView.findViewById(R.id.tvBlockName)
        tvBlockName?.text = timeSheet.blockName()

        createAvatarBackground()
        initRateType()
        initRowTimeSheet()

        val jobNameResourceId =
            if (timeSheet.typeJob == TypeJob.PRUNING) R.string.pruning else R.string.thinning
        val jobName = context.resources.getString(jobNameResourceId)
        val wagesDescription = context.resources.getString(R.string.wages_rate_description, jobName)
        tvWagesDescription?.text = wagesDescription

        return rootView
    }

    fun updatePieceRateValue(newRate: String) {
        edtPieceRate?.setText(newRate)
    }

    private fun createAvatarBackground() {
        val backgroundColor = generateAvatarBackgroundColor()
        AppCompatResources.getDrawable(context, R.drawable.background_staff_name)?.let { drawable ->
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(wrappedDrawable, backgroundColor)
            tvStaffAvatar?.background = wrappedDrawable
        }
        tvStaffAvatar?.setText(timeSheet.staff.characterForAvatar())
    }

    private fun generateAvatarBackgroundColor(): Int {
        val index = Random.nextInt(0, listAvatarBackgroundColor.size)
        return Color.parseColor(listAvatarBackgroundColor[index])
    }

    private fun initRateType() {

        btnPieceRate?.setOnClickListener {
            if (currentRateType != RateType.PIECE_RATE) {
                updateRateType(RateType.PIECE_RATE)
            }
        }

        btnWages?.setOnClickListener {
            if (currentRateType != RateType.WAGES) {
                updateRateType(RateType.WAGES)
            }
        }

        rootView.findViewById<TextView>(R.id.btnApplyToAll).setOnClickListener {
            edtPieceRate?.text?.toString()?.let { rate ->
                timeSheetViewModel.updatePieceRateValue(
                    rate = rate,
                    typeJob = timeSheet.typeJob,
                    timeSheetId = timeSheet.timeSheetId,
                    isUpdateForAll = true
                )
            }
        }

        edtPieceRate?.addTextChangedListener { text ->
            val rate = text.toString()
            timeSheetViewModel.updatePieceRateValue(
                rate = rate,
                typeJob = timeSheet.typeJob,
                timeSheetId = timeSheet.timeSheetId,
                isUpdateForAll = false
            )
        }

    }

    private fun updateRateType(newType: RateType) {
        currentRateType = newType
        val selectedBackground =
            AppCompatResources.getDrawable(context, R.drawable.background_rate_type_button_selected)
        val unSelectedBackground = AppCompatResources.getDrawable(
            context,
            R.drawable.background_rate_type_button_unselected
        )

        when (currentRateType) {
            RateType.PIECE_RATE -> {
                btnPieceRate?.setTextColor(R.color.white)
                btnPieceRate?.background = selectedBackground
                layoutPieceRateValue?.visibility = View.VISIBLE

                tvWagesDescription?.visibility = View.GONE
                btnWages?.setTextColor(R.color.text_color)
                btnWages?.background = unSelectedBackground
            }
            else -> {
                btnPieceRate?.setTextColor(R.color.text_color)
                btnPieceRate?.background = unSelectedBackground
                layoutPieceRateValue?.visibility = View.GONE

                btnWages?.setTextColor(R.color.white)
                tvWagesDescription?.visibility = View.VISIBLE
                btnWages?.background = selectedBackground
            }
        }

        timeSheetViewModel.changeRateType(currentRateType, getTimeSheetId(), typeJob = timeSheet.typeJob)
    }

    private fun initRowTimeSheet() {
        val rowComponent =
            RowComponent(
                context = context,
                listRowTimeSheet = timeSheet.block.listRowTimeSheet,
                timeSheetId = getTimeSheetId(),
                typeJob = timeSheet.typeJob,
                timeSheetViewModel = timeSheetViewModel
            )
        llRows?.addView(rowComponent.createView())
    }

    private fun getTimeSheetId(): Int {
        return timeSheet.timeSheetId
    }

}