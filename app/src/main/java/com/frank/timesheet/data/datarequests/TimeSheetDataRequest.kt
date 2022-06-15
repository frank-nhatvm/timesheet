package com.frank.timesheet.data.datarequests

data class TimeSheetItemsDataRequest(
    val items: List<TimeSheetDataRequest>
)

data class TimeSheetDataRequest(
    val time_sheet_id: Int,
    val staff_id:Int,
    val block: BlockTimeSheetDataRequest,
    val orchard_name: String,
    val rate_type: String,
    val rate_value: String? =null,
    val type_job: String
)

data class BlockTimeSheetDataRequest(val block_id: Int, val rows : List<RowTimeSheetDataRequest>)

data class RowTimeSheetDataRequest(val row_id: Int, val tree_number: Int)
