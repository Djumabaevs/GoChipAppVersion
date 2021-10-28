package com.djumabaevs.gochipapp.util

import android.app.NotificationChannel
import android.content.Context
import android.os.Build

class NotificationUtil {

    fun createInboxStyleNotificationChannel(context: Context): String {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = InboxStyleMockData.mChannelId
            val channelName = InboxStyleMockData.mChannelName
            val channelDescription = InboxStyleMockData.mChannelDescription
            val channelImportance = InboxStyleMockData.mChannelImportance
            val channelEnableVibrate: Boolean = InboxStyleMockData.mChannelEnableVibrate
            val channelLockscreenVisibility: Int = InboxStyleMockData.mChannelLockScreenVisibility

            val notificationChannel = NotificationChannel(channelID, channelName,channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(channelEnableVibrate)
            notificationChannel.lockscreenVisibility = channelLockscreenVisibility

            val notificationManager =
        }
    }
}