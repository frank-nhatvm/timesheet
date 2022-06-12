package com.frank.timesheet.data.repositories

import com.frank.timesheet.data.entities.Orchard
import com.frank.timesheet.data.entities.OrchardBlock
import com.frank.timesheet.data.entities.Row

class OrchardRepository {

    fun getCurrentOrchard(): Orchard {

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
        val ub13Block =
            OrchardBlock(
                blockId = 13,
                blockName = "UB13",
                listRows = listOf(row3Block13, row4Block13, row5Block13)
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
        val ub14Block =
            OrchardBlock(
                blockId = 14,
                blockName = "UB14",
                listRows = listOf(row3Block14, row4Block14,row5Block14)
            )

        return Orchard(
            orchardId = 191,
            orchardName = "Benjil",
            listBlock = listOf(ub13Block, ub14Block)
        )
    }

}