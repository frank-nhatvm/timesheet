package com.frank.timesheet.data.repositories

import com.frank.timesheet.data.datarequests.TimeSheetItemsDataRequest
import com.frank.timesheet.data.entities.*

class TimeSheetRepository {

    fun getTimeSheet(): List<TimeSheet> {

        val barco = Staff(
            staffId = 1,
            firstName = "Barco"
        )

        val henryPham = Staff(
            staffId = 2,
            firstName = "Herry",
            lastName = "Pham"
        )

        val darijan = Staff(
            staffId = 3,
            firstName = "Darijan",
        )

        val yiwan = Staff(
            staffId = 4,
            firstName = "Yi Wan"
        )

        val elizabethJargrave = Staff(
            staffId = 5,
            firstName = "Elizabeth",
            lastName = "Jargrave"
        )

        val row3Block14 = Row(
            rowId = 89,
            rowName = "3",
            maxTreeNumber = 480
        )
        val row4Block14 = Row(
            rowId = 90,
            rowName = "4",
            maxTreeNumber = 556
        )

        val row5Block14 = Row(
            rowId = 91,
            rowName = "5",
            maxTreeNumber = 270
        )

        val row3Block13 = Row(
            rowId = 3,
            rowName = "3",
            maxTreeNumber = 480,
        )
        val row4Block13 = Row(
            rowId = 4,
            rowName = "4",
            maxTreeNumber = 556,
        )

        val row5Block13 = Row(
            rowId = 5,
            rowName = "5",
            maxTreeNumber = 270,
        )


        val yiwanPruningRow4Block13Task = Task(
            taskId = 19,
            staff = yiwan,
            treeNumber = 250
        )

        val elizabethJargravePruningRow5Block13Task = Task(
            taskId = 20,
            staff = elizabethJargrave,
            treeNumber = 100
        )

        val row3Block13TimeSheet = RowTimeSheet(
            row = row3Block13,
        )

        val row4Block13TimeSheet = RowTimeSheet(
            row = row4Block13,
            listTask = listOf(
                yiwanPruningRow4Block13Task
            )
        )

        val row5Block13TimeSheet = RowTimeSheet(
            row = row5Block13,
            listTask = listOf(elizabethJargravePruningRow5Block13Task)
        )

        val block13TimeSheet = BlockTimeSheet(
            blockId = 10,
            blockName = "UB13",
            listRowTimeSheet = listOf(
                row3Block13TimeSheet,
                row4Block13TimeSheet,
                row5Block13TimeSheet
            )
        )

        val barcoPruningTimeSheet = TimeSheet(
            timeSheetId = 99,
            staff = barco,
            block = block13TimeSheet,
            orchardName = "Benjil",
            typeJob = TypeJob.PRUNING,
        )

        val henryPhamPruningTimeSheet = TimeSheet(
            timeSheetId = 991,
            staff = henryPham,
            block = block13TimeSheet,
            orchardName = "Benjil",
            typeJob = TypeJob.PRUNING,
        )


        val elizabethJargraveThinningRow5Block14Task = Task(
            taskId = 30,
            staff = elizabethJargrave,
            treeNumber = 100
        )

        val yiwanThinningRow4Block14Task = Task(
            taskId = 29,
            staff = yiwan,
            treeNumber = 250
        )

        val row3Block14TimeSheet = RowTimeSheet(
            row = row3Block14,
        )

        val row4Block14TimeSheet = RowTimeSheet(
            row = row4Block14,
            listTask = listOf(yiwanThinningRow4Block14Task)
        )

        val row5Block14TimeSheet = RowTimeSheet(
            row = row5Block14,
            listTask = listOf(elizabethJargraveThinningRow5Block14Task)
        )

        val block14TimeSheet = BlockTimeSheet(
            blockId = 11,
            blockName = "UB14",
            listRowTimeSheet = listOf(
                row3Block14TimeSheet,
                row4Block14TimeSheet,
                row5Block14TimeSheet
            )
        )

        val dariJanThinningTimeSheet = TimeSheet(
            timeSheetId = 992,
            staff = darijan,
            orchardName = "Benjil",
            block = block14TimeSheet,
            typeJob = TypeJob.THINNING
        )

        return listOf(barcoPruningTimeSheet, henryPhamPruningTimeSheet, dariJanThinningTimeSheet)
    }


    fun saveTimeSheet(timeSheetItemsDataRequest: TimeSheetItemsDataRequest){
        // call to remote data source
    }
}

