package com.djumabaevs.gochipapp.util

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeRequestWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {
        val inputValue = inputData.getString("inputKey")
        Log.i("Worker", "$inputValue")
    }

    object Companion {
        fun logger(message: String) = Log.i("WorkRequest status", message)
    }

}