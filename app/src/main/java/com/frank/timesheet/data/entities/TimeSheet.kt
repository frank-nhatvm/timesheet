package com.frank.timesheet.data.entities


class TimeSheet(
    val timeSheetId: Int,
    val staff: Staff,
    val block: BlockTimeSheet,
    val orchardName: String,
    var rateType: RateType = RateType.PIECE_RATE,
    var rateValue: String = "",
    val typeJob: TypeJob,
) {
    fun blockName(): String {
        return block.blockName
    }
}

class BlockTimeSheet(
    val blockId: Int,
    val blockName: String,
    val listRowTimeSheet: List<RowTimeSheet>
) {
    fun toggleRow(rowId: Int, isSelected: Boolean) {
        listRowTimeSheet.forEach { rowTimeSheet ->
            if (rowTimeSheet.row.rowId == rowId) {
                rowTimeSheet.isSelected = isSelected
            }
        }
    }

    fun updateTreeNumberForRow(rowId: Int, treeNumber: Int) {
        listRowTimeSheet.forEach { rowTimeSheet ->
            if (rowTimeSheet.row.rowId == rowId) {
                rowTimeSheet.treeOfCurrentCustomer = treeNumber
            }
        }
    }

    fun updateMaxTree(mapStaffWorkingRow:Map<Int,Int>, shouldAddRemainderTree: Boolean){
        listRowTimeSheet.forEach { rowTimeSheet ->
            if (rowTimeSheet.isSelected) {
                val rowId = rowTimeSheet.row.rowId
                val numberStaffWorkingOnThisRow = mapStaffWorkingRow[rowId] ?: 1
                val availableTree = rowTimeSheet.getAvailableTreeNumber()
                var maxTree = availableTree / numberStaffWorkingOnThisRow
                val remainderTree = availableTree % numberStaffWorkingOnThisRow
                if (shouldAddRemainderTree) {
                    maxTree += remainderTree
                }
                rowTimeSheet.treeOfCurrentCustomer = maxTree
            }

        }
    }

}

class RowTimeSheet(
    val row: Row,
    val listTask: List<Task> = emptyList(),
    var treeOfCurrentCustomer: Int = 0,
    var isSelected: Boolean = false
) {

    fun getListJobInString(): String {
        val stringBuilder = StringBuilder()
        listTask.forEach { task ->
            val staffFullName = task.staff.fullName()
            stringBuilder.append("$staffFullName(${task.treeNumber})")
            stringBuilder.append(" ")
        }
        return stringBuilder.toString()
    }

    fun getMaxTreeNumber(): Int {
        return row.maxTreeNumber
    }

    fun getDoneTreeNumberOfOtherStaffs(): Int {
        return listTask.sumOf { it.treeNumber }
    }

    fun getAvailableTreeNumber(): Int {
        return (getMaxTreeNumber() - (treeOfCurrentCustomer + getDoneTreeNumberOfOtherStaffs()))
    }

    fun hasDoneTask(): Boolean {
        return listTask.isNotEmpty()
    }

}

data class Task(
    val taskId: Int,
    val staff: Staff,
    val treeNumber: Int,
)

enum class RateType(val type: String) {
    PIECE_RATE("1"),
    WAGES("2"),
}

enum class TypeJob(val type: String) {
    PRUNING("1"),
    THINNING("2"),
}