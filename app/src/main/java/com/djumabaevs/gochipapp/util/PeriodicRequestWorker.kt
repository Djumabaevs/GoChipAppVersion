package com.djumabaevs.gochipapp.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicRequestWorker(context: Context, params: WorkerParameters): Worker(context, params) {

    override fun doWork(): Result {

    }

}