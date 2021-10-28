package com.djumabaevs.gochipapp.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class PeriodicRequestWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {

    }

    private fun getDate(milliSeconds: Long) : String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}