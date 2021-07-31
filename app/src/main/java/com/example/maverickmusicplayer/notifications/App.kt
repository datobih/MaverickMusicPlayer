package com.example.maverickmusicplayer.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App(): Application() {
companion object {
    val CHANNEL_1 = "channel1"
}
    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            var notificationManager=getSystemService(NotificationManager::class.java)
            var notificationChannel=NotificationChannel(CHANNEL_1,"Channel Music",NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description="Music Notification Channel"

            notificationManager.createNotificationChannel(notificationChannel)


        }




    }
}