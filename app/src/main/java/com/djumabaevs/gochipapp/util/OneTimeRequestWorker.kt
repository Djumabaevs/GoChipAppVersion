package com.djumabaevs.gochipapp.util

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeRequestWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {
        val inputValue = inputData.getString("inputKey")
        Log.i("Worker", "$inputValue")

        //Put your background tasks here

        return Result.success(createOutputData())
    }

    private fun createOutputData(): Data {
        return Data.Builder().putString("outputKey", "Output value").build()
    }

    object Companion {
        fun logger(message: String) = Log.i("WorkRequest status", message)
    }

}