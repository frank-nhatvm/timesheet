package com.frank.timesheet.data.entities



class TimeSheet(
    val timeSheetId: Int,
    val staff: Staff,
    val block: BlockTimeSheet,
    val orchardName:String,
    var rateType: RateType = RateType.PIECE_RATE,
    var rateValue: Int = -1,
    val typeJob: TypeJob,
) {
    fun blockName():String{
        return block.blockName
    }

}

data class BlockTimeSheet(
    val blockId: Int,
    val blockName: String,
    val listRowTimeSheet: List<RowTimeSheet>
)

class RowTimeSheet(
    val row: Row,
    val listTask : List<Task> = emptyList(),
    var treeOfCurrentCustomer: Int = 0
){
    fun getMaxTreeNumber(): Int{
        return row.maxTreeNumber
    }

    fun getDoneTreeNumberOfOtherStaffs(): Int{
        return listTask.sumOf { it.treeNumber }
    }

    fun getAvailableTreeNumber():Int{
        return (getMaxTreeNumber() - (treeOfCurrentCustomer + getDoneTreeNumberOfOtherStaffs()))
    }
}

data class Task(
    val taskId: Int,
    val staff: Staff,
    val treeNumber: Int,
)

enum class RateType(val type:String){
    PIECE_RATE("1"),
    WAGES("2"),
}

enum class TypeJob(val  type: String){
    PRUNING("1"),
    THINNING("2"),
}