package com.example.maverickmusicplayer.activities

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.notifications.App

class PlaybackService: Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(intent?.action=="end"){

            stopSelf()
            stopForeground(true)


        }
var art=Constants.mainActivity!!.currentMusic!!.albumArt



        var notification= NotificationCompat.Builder(this, App.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_m_icon)
                .setLargeIcon(art)

                .setContentTitle( Constants.mainActivity!!.currentMusic!!.name)
                .setContentText( Constants.mainActivity!!.currentMusic!!.artist)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSilent(true)
                .setAutoCancel(true)
                .setProgress(0,0,false)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(Constants.mainActivity!!.contentPendingIntent)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2).setMediaSession(Constants.mainActivity!!.mediaSession!!.sessionToken))
                .addAction(R.drawable.baseline_skip_previous_24,"Previous",Constants.mainActivity!!.pendingPrevious)
                .addAction(Constants.mainActivity!!.notifPause,"Pause",Constants.mainActivity!!.pendingPause)
                .addAction(R.drawable.baseline_skip_next_24,"Next",Constants.mainActivity!!.pendingNext)
                .build()

        startForeground(1,notification)

        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    override fun onBind(intent: Intent?): IBinder? {


      return null
    }

    override fun onDestroy() {

        super.onDestroy()
    }

}