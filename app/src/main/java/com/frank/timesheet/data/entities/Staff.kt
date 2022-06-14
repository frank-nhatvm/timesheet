package com.frank.timesheet.data.entities

import java.lang.StringBuilder

class Staff(
    val staffId: Int,
    val firstName: String? = null,
    val lastName: String? = null
) {
    fun fullName(): String {
        val builder = StringBuilder()
        if (firstName?.isNotEmpty() == true) {
            builder.append(firstName)
        }
        if (lastName?.isNotEmpty() == true) {
            builder.append(" ")
            builder.append(lastName)
        }
        return builder.toString()
    }

    fun characterForAvatar(): String {
        val builder = StringBuilder()
        if (firstName?.isNotEmpty() == true) {
            builder.append(firstName.first().toString())
        }
        if (lastName?.isNotEmpty() == true) {
            builder.append(lastName.first().toString())
        }
        return builder.toString()
    }
}
