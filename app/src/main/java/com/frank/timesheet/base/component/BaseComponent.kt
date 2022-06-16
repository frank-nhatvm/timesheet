package com.frank.timesheet.base.component

import android.content.Context
import android.view.View

abstract class BaseComponent constructor(protected val context:Context) {

    protected lateinit var rootView: View

    abstract  fun createView():View

}