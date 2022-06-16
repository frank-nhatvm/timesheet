package com.frank.timesheet.ui.timesheet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frank.timesheet.R
import com.frank.timesheet.common.Utils
import com.frank.timesheet.data.entities.RateType
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.databinding.AdapterTimeSheetBinding
import com.frank.timesheet.ui.timesheet.components.RowComponent
import androidx.recyclerview.widget.ListAdapter

class TimeSheetAdapter constructor(private val timeSheetViewModel: TimeSheetViewModel) :
    ListAdapter<TimeSheet, TimeSheetAdapter.TimeSheetViewHolder>(TimeSheetDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSheetViewHolder {
        return TimeSheetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TimeSheetViewHolder, position: Int) {
        val timeSheet = getItem(position)
        holder.bind(timeSheet, timeSheetViewModel)
    }

    class TimeSheetViewHolder private constructor(private val adapterTimeSheetBinding: AdapterTimeSheetBinding) :
        RecyclerView.ViewHolder(adapterTimeSheetBinding.root) {
        private var currentRateType = RateType.PIECE_RATE

        companion object {
            fun from(parent: ViewGroup): TimeSheetViewHolder {
                val binding = AdapterTimeSheetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TimeSheetViewHolder(binding)
            }
        }

        fun bind(timeSheet: TimeSheet, timeSheetViewModel: TimeSheetViewModel) {
            adapterTimeSheetBinding.tvOrchardName.text = timeSheet.orchardName
            adapterTimeSheetBinding.tvBlockName.text = timeSheet.blockName()
            adapterTimeSheetBinding.edtPieceRate.setText(timeSheet.rateValue)

            val context = adapterTimeSheetBinding.root.context

            createAvatarBackground(context, timeSheet)
            initRateType(context, timeSheet, timeSheetViewModel)
            initRowTimeSheet(context, timeSheet, timeSheetViewModel)

            val jobNameResourceId =
                if (timeSheet.typeJob == TypeJob.PRUNING) R.string.pruning else R.string.thinning
            val jobName = context.resources.getString(jobNameResourceId)
            val wagesDescription =
                context.resources.getString(R.string.wages_rate_description, jobName)
            adapterTimeSheetBinding.tvWagesDescription.text = wagesDescription

        }


        private fun createAvatarBackground(context: Context, timeSheet: TimeSheet) {
            val backgroundColor = Utils.generateAvatarBackgroundColor()
            AppCompatResources.getDrawable(
                context,
                R.drawable.background_staff_name
            )?.let { drawable ->
                val wrappedDrawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(wrappedDrawable, backgroundColor)
                adapterTimeSheetBinding.tvStaffAvatar.background = wrappedDrawable
            }
            adapterTimeSheetBinding.tvStaffAvatar.setText(timeSheet.staff.characterForAvatar())
        }


        private fun initRateType(
            context: Context,
            timeSheet: TimeSheet,
            timeSheetViewModel: TimeSheetViewModel
        ) {

            adapterTimeSheetBinding.btnPieceRate.setOnClickListener {
                if (currentRateType != RateType.PIECE_RATE) {
                    updateRateType(context, RateType.PIECE_RATE, timeSheetViewModel, timeSheet)
                }
            }

            adapterTimeSheetBinding.btnWages.setOnClickListener {
                if (currentRateType != RateType.WAGES) {
                    updateRateType(context, RateType.WAGES, timeSheetViewModel, timeSheet)
                }
            }

            adapterTimeSheetBinding.btnApplyToAll.setOnClickListener {
                adapterTimeSheetBinding.edtPieceRate.text?.toString()?.let { rate ->
                    timeSheetViewModel.updatePieceRateValue(
                        rate = rate,
                        typeJob = timeSheet.typeJob,
                        timeSheetId = timeSheet.timeSheetId,
                        isUpdateForAll = true
                    )
                }
            }

            adapterTimeSheetBinding.edtPieceRate.addTextChangedListener { text ->
                val rate = text.toString()
                timeSheetViewModel.updatePieceRateValue(
                    rate = rate,
                    typeJob = timeSheet.typeJob,
                    timeSheetId = timeSheet.timeSheetId,
                    isUpdateForAll = false
                )
            }

        }

        private fun updateRateType(
            context: Context,
            newType: RateType,
            timeSheetViewModel: TimeSheetViewModel,
            timeSheet: TimeSheet
        ) {
            currentRateType = newType

            val selectedBackground =
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.background_rate_type_button_selected
                )
            val unSelectedBackground = AppCompatResources.getDrawable(
                context,
                R.drawable.background_rate_type_button_unselected
            )

            when (currentRateType) {
                RateType.PIECE_RATE -> {
                    adapterTimeSheetBinding.btnPieceRate.setTextColor(R.color.white)
                    adapterTimeSheetBinding.btnPieceRate.background = selectedBackground
                    adapterTimeSheetBinding.layoutPieceRateValue.visibility = View.VISIBLE

                    adapterTimeSheetBinding.tvWagesDescription.visibility = View.GONE
                    adapterTimeSheetBinding.btnWages.setTextColor(R.color.text_color)
                    adapterTimeSheetBinding.btnWages.background = unSelectedBackground
                }
                else -> {
                    adapterTimeSheetBinding.btnPieceRate.setTextColor(R.color.text_color)
                    adapterTimeSheetBinding.btnPieceRate.background = unSelectedBackground
                    adapterTimeSheetBinding.layoutPieceRateValue.visibility = View.GONE

                    adapterTimeSheetBinding.btnWages.setTextColor(R.color.white)
                    adapterTimeSheetBinding.tvWagesDescription.visibility = View.VISIBLE
                    adapterTimeSheetBinding.btnWages.background = selectedBackground
                }
            }

            timeSheetViewModel.changeRateType(
                currentRateType,
                timeSheet.timeSheetId,
                typeJob = timeSheet.typeJob
            )
        }

        private fun initRowTimeSheet(
            context: Context,
            timeSheet: TimeSheet,
            timeSheetViewModel: TimeSheetViewModel
        ) {
            val rowComponent = RowComponent(
                context = context,
                listRowTimeSheet = timeSheet.block.listRowTimeSheet,
                timeSheetId = timeSheet.timeSheetId,
                typeJob = timeSheet.typeJob,
                timeSheetViewModel = timeSheetViewModel
            )
            adapterTimeSheetBinding.llRows.addView(rowComponent.createView())
        }
    }

    class TimeSheetDiffUtil : DiffUtil.ItemCallback<TimeSheet>() {
        override fun areItemsTheSame(oldItem: TimeSheet, newItem: TimeSheet): Boolean {
            return oldItem.timeSheetId == newItem.timeSheetId
        }

        override fun areContentsTheSame(oldItem: TimeSheet, newItem: TimeSheet): Boolean {
            return oldItem.timeSheetId == newItem.timeSheetId
        }
    }
}