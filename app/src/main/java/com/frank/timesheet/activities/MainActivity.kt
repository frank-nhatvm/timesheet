package com.frank.timesheet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frank.timesheet.R
import com.frank.timesheet.common.DataLocal
import com.frank.timesheet.data.repositories.OrchardRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}