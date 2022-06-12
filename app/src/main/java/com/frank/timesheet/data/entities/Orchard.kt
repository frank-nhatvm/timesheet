package com.frank.timesheet.data.entities

data class Orchard(
    val orchardId: Int,
    val orchardName: String,
    val listBlock: List<OrchardBlock>
)

data class OrchardBlock(
    val blockId: Int,
    val blockName: String,
    val listRows : List<Row>
)

data class Row(
    val rowId: Int,
    val rowName: String,
    val maxTreeNumber: Int,
)
