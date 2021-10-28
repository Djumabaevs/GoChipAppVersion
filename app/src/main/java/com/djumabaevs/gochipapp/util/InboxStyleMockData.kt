package com.djumabaevs.gochipapp.util

import android.app.Notification
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

object InboxStyleMockData {
    const val mContentTitle = "5 new emails"
    const val mContentText = "from Gavin, from Leo +3 more"
    const val mNumberOfNewEmails = 5
    const val mPriority = NotificationCompat.PRIORITY_DEFAULT
    const val mBigContentTitle = "5 new emails from Gavin, from Leo +3 more"
    const val mSummaryText = "New emails"

    fun mIndividualEmailSummary(): ArrayList<String> {
        val list = ArrayList<String>()

        list.add("Gavin Jackson - Do it boy!")
        list.add("Leo - it was not my mistake")
        list.add("Dmitriy - where is new APK?")
        list.add("Alexey - the map was working yesterday")
        list.add("Sagynbek - give me new task")

        return list
    }

    fun mIndividualEmailParticipants(): ArrayList<String> {
        val list = ArrayList<String>()

        list.add("Gavin Jackson")
        list.add("Leo")
        list.add("Dmitriy")
        list.add("")
        list.add("Sagynbek")

        return list
    }

    const val mChannelId = "channel_email_1"
    const val mChannelName = "Sample email"

    const val mChannelDescription = "Sample email notifications"
    const val mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT
    const val mChannelEnableVibrate = true
    const val mChannelLockScreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
}