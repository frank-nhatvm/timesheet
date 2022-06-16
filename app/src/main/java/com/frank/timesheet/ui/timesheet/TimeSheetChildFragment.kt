package com.frank.timesheet.ui.timesheet

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class TimeSheetChildFragment : Fragment() {
    companion object {
        const val TYPE_PAGE = "type_page"
    }
    private val viewModel by viewModels<TimeSheetViewModel> ({ requireParentFragment() })

}