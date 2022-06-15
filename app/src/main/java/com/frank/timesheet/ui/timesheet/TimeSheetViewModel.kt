package com.frank.timesheet.ui.timesheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frank.timesheet.common.Event
import com.frank.timesheet.data.entities.RateType
import com.frank.timesheet.data.entities.TimeSheet
import com.frank.timesheet.data.entities.TypeJob
import com.frank.timesheet.data.repositories.TimeSheetRepository

class TimeSheetViewModel constructor(private val timeSheetRepository: TimeSheetRepository = TimeSheetRepository()) :
    ViewModel() {

    private var _listPruningTimeSheet = MutableLiveData<List<TimeSheet>>()
    val listPruningTimeSheet: LiveData<List<TimeSheet>>
        get() = _listPruningTimeSheet

    private var _listThinningTimeSheet = MutableLiveData<List<TimeSheet>>()
    val listThinningTimeSheet: LiveData<List<TimeSheet>>
        get() = _listThinningTimeSheet

    private var _pruningTimeSheetAddMaxTree = MutableLiveData<Event<Boolean>>()
    val pruningTimeSheetAddMaxTree: LiveData<Event<Boolean>>
        get() = _pruningTimeSheetAddMaxTree

    private var _thinningTimeSheetAddMaxTree = MutableLiveData<Event<Boolean>>()
    val thinningTimeSheetAddMaxTree: LiveData<Event<Boolean>>
        get() = _thinningTimeSheetAddMaxTree

    fun fetchData() {
        val timeSheets = timeSheetRepository.getTimeSheet()
        val pruningTimeSheets = timeSheets.filter { it.typeJob == TypeJob.PRUNING }
        _listPruningTimeSheet.postValue(pruningTimeSheets)

        val thinningTimeSheets = timeSheets.filter { it.typeJob == TypeJob.THINNING }
        _listThinningTimeSheet.postValue(thinningTimeSheets)
    }

    fun changeRateType(typeRateType: RateType, timeSheetId: Int, typeJob: TypeJob) {
        if (typeJob == TypeJob.PRUNING) {
            changeRateTypeForPruning(typeRateType = typeRateType, timeSheetId = timeSheetId)
        } else {
            changeRateTypeForThinning(typeRateType = typeRateType, timeSheetId = timeSheetId)
        }
    }

    private fun changeRateTypeForPruning(typeRateType: RateType, timeSheetId: Int) {
        listPruningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.rateType = typeRateType
            _listPruningTimeSheet.postValue(currentList)
        }
    }

    private fun changeRateTypeForThinning(typeRateType: RateType, timeSheetId: Int) {
        listThinningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.rateType = typeRateType
            _listThinningTimeSheet.postValue(currentList)
        }
    }

    fun updatePieceRateValue(
        rate: String,
        typeJob: TypeJob,
        timeSheetId: Int,
        isUpdateForAll: Boolean = false
    ) {
        if (isUpdateForAll) {
            updatePieceRateForAll(rate = rate, typeJob = typeJob, timeSheetId = timeSheetId)
        } else {
            updatePieceRateForOneTimeSheet(
                rate = rate,
                timeSheetId = timeSheetId,
                typeJob = typeJob
            )
        }
    }

    private fun updatePieceRateForAll(
        rate: String,
        typeJob: TypeJob,
        timeSheetId: Int
    ) {
        if (typeJob == TypeJob.PRUNING) {
            updatePieceRateForAllPruningTimeSheet(rate = rate, timeSheetId = timeSheetId)
        } else {
            updatePieceRateForAllThinningTimeSheet(rate = rate, timeSheetId = timeSheetId)
        }
    }

    private fun updatePieceRateForAllPruningTimeSheet(
        rate: String, timeSheetId: Int,
    ) {
        listPruningTimeSheet.value?.let { currentList ->
            currentList.forEach { timeSheet ->
                if (timeSheet.timeSheetId != timeSheetId && timeSheet.rateType == RateType.PIECE_RATE) {
                    timeSheet.rateValue = rate
                }
            }
            _listPruningTimeSheet.postValue(currentList)
        }
    }

    private fun updatePieceRateForAllThinningTimeSheet(rate: String, timeSheetId: Int) {
        listThinningTimeSheet.value?.let { currentList ->
            currentList.forEach { timesheet ->
                if (timesheet.timeSheetId != timeSheetId && timesheet.rateType == RateType.PIECE_RATE) {
                    timesheet.rateValue = rate
                }
            }
            _listThinningTimeSheet.postValue(currentList)
        }
    }

    private fun updatePieceRateForOneTimeSheet(
        rate: String,
        typeJob: TypeJob,
        timeSheetId: Int,
    ) {
        if (typeJob == TypeJob.PRUNING) {
            updatePieceRateForOnePruningTimeSheet(rate = rate, timeSheetId = timeSheetId)
        } else {
            updatePieceRateForOneThinningTimeSheet(rate = rate, timeSheetId = timeSheetId)
        }
    }

    private fun updatePieceRateForOnePruningTimeSheet(rate: String, timeSheetId: Int) {
        listPruningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.rateValue = rate
            _listPruningTimeSheet.postValue(currentList)
        }
    }

    private fun updatePieceRateForOneThinningTimeSheet(rate: String, timeSheetId: Int) {
        listThinningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.rateValue = rate
            _listThinningTimeSheet.postValue(currentList)
        }
    }

    fun toggleRow(timeSheetId: Int, rowId: Int, isSelected: Boolean, typeJob: TypeJob) {
        if (typeJob == TypeJob.PRUNING) {
            toggleRowForPruning(timeSheetId = timeSheetId, rowId = rowId, isSelected = isSelected)
        } else {
            toggleRowForThinning(timeSheetId = timeSheetId, rowId = rowId, isSelected = isSelected)
        }
    }

    private fun toggleRowForPruning(timeSheetId: Int, rowId: Int, isSelected: Boolean) {
        listPruningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.block?.toggleRow(
                rowId = rowId,
                isSelected = isSelected
            )
            _listPruningTimeSheet.postValue(currentList)
        }
    }

    private fun toggleRowForThinning(timeSheetId: Int, rowId: Int, isSelected: Boolean) {
        listThinningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.block?.toggleRow(
                rowId = rowId,
                isSelected = isSelected
            )
            _listThinningTimeSheet.postValue(currentList)
        }
    }

    fun updateTreeNumberForRow(treeNumber: Int, rowId: Int, timeSheetId: Int, typeJob: TypeJob) {
        if (typeJob == TypeJob.PRUNING) {
            updateTreeNumberForRowPruning(
                treeNumber = treeNumber,
                rowId = rowId,
                timeSheetId = timeSheetId
            )
        } else {
            updateTreeNumberForRowThinning(
                treeNumber = treeNumber,
                rowId = rowId,
                timeSheetId = timeSheetId
            )
        }
    }

    private fun updateTreeNumberForRowPruning(treeNumber: Int, rowId: Int, timeSheetId: Int) {
        listPruningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.block?.updateTreeNumberForRow(
                rowId = rowId,
                treeNumber = treeNumber
            )
        }
    }

    private fun updateTreeNumberForRowThinning(treeNumber: Int, rowId: Int, timeSheetId: Int) {
        listThinningTimeSheet.value?.let { currentList ->
            currentList.find { it.timeSheetId == timeSheetId }?.block?.updateTreeNumberForRow(
                rowId = rowId,
                treeNumber = treeNumber
            )
            _listThinningTimeSheet.postValue(currentList)
        }
    }

    fun addMaxTree(typeJob: TypeJob) {
        if (typeJob == TypeJob.PRUNING) {
            addMaxTreeForPruning()
        } else {
            addMaxTreeForThinning()
        }
    }

    private fun addMaxTreeForPruning() {
        _pruningTimeSheetAddMaxTree.postValue(Event(false))
        listPruningTimeSheet.value?.let { currentList ->
            val mapStaffWorkingRow = getStaffWorkingRow(currentList)
            currentList.forEachIndexed { index, timeSheet ->
                val shouldAddRemainderTree = (index == currentList.lastIndex)
                timeSheet.block.updateMaxTree(
                    mapStaffWorkingRow = mapStaffWorkingRow,
                    shouldAddRemainderTree = shouldAddRemainderTree
                )
            }
            _listPruningTimeSheet.postValue(currentList)
            _pruningTimeSheetAddMaxTree.postValue(Event(true))
        }
    }

    private fun addMaxTreeForThinning() {
        _thinningTimeSheetAddMaxTree.postValue(Event(false))
        listThinningTimeSheet.value?.let { currentList ->
            val mapStaffWorkingRow = getStaffWorkingRow(currentList)
            currentList.forEachIndexed { index, timeSheet ->
                val shouldAddRemainderTree = (index == currentList.lastIndex)
                timeSheet.block.updateMaxTree(
                    mapStaffWorkingRow = mapStaffWorkingRow,
                    shouldAddRemainderTree = shouldAddRemainderTree
                )
            }
            _listThinningTimeSheet.postValue(currentList)
            _thinningTimeSheetAddMaxTree.postValue(Event(true))
        }
    }

    /**
     * Count the number of staff who are working on the same row.
     */
    private fun getStaffWorkingRow(list: List<TimeSheet>): Map<Int, Int> {
        val hm = mutableMapOf<Int, Int>()

        list.forEach { timeSheet ->
            timeSheet.block.listRowTimeSheet.forEach { rowTimeSheet ->
                if (rowTimeSheet.isSelected) {
                    val rowId = rowTimeSheet.row.rowId
                    if (hm.containsKey(rowId)) {
                        val count = hm[rowId] ?: 1
                        hm[rowId] = count + 1
                    } else {
                        hm[rowId] = 1
                    }
                }
            }
        }

        return hm
    }


}