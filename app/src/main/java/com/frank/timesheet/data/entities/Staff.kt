package com.frank.timesheet.data.entities

class Staff(
    val staffId: Int,
    val firstName: String? = null,
    val lastName: String? = null
)
{
    fun fullName(): String{
        return "$firstName $lastName"
    }

    fun characterForAvatar(): String{
        return firstName?.first()?.toString() ?: ""
    }
}
