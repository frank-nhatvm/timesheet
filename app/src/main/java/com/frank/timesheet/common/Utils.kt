package com.frank.timesheet.common

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.random.Random

object Utils {
    fun dpToPixel(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun hideSoftKeyboard(view: View?, context: Context) {
        val imm: InputMethodManager? =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun showSoftKeyboard(view:View?,context: Context){
        val imm: InputMethodManager? =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT)
    }

    fun generateAvatarBackgroundColor(): Int{
         val listAvatarBackgroundColor =
            listOf("#cc6699", "#993366", "#993333", "#800000", "#993300")
        val index = Random.nextInt(0, listAvatarBackgroundColor.size)
        return Color.parseColor(listAvatarBackgroundColor[index])
    }

}