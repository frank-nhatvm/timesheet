package com.frank.timesheet.ui.timesheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.data.repositories.TimeSheetRepository

class TimeSheetViewModel constructor(private val timeSheetRepository: TimeSheetRepository = TimeSheetRepository()) :
    ViewModel() {

    private var _listTimesheet = MutableLiveData<List<TimeSheet>>()
     val listTimesheet: LiveData<List<TimeSheet>>
        get() = _listTimesheet

    fun fetchData() {
        val timeSheets = timeSheetRepository.getTimeSheet()
        _listTimesheet.postValue(timeSheets)
    }

}