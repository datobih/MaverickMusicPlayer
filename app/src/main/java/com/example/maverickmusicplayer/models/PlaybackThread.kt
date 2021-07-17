package com.example.maverickmusicplayer.models

import android.content.Context
import android.icu.util.ValueIterator
import android.media.MediaPlayer
import android.os.Looper
import android.view.View
import com.example.maverickmusicplayer.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Handler

class PlaybackThread(val context: Context,val musicList: ArrayList<Music>,var position:Int): Thread() {
    public var stopp = false
    public var isPaused = false

    @Synchronized
    override fun run() {


        while (this.isInterrupted == false) {
            if (position <= musicList.lastIndex) {

                if (context is MainActivity) {

                    if (context.isPaused == true) {
                        isPaused = true
                    }


                    if (context.mediaPlayer != null) {
                        context.mediaPlayer?.stop()
                        context.mediaPlayer?.release()
                        context.mediaPlayer = null
                    }

                    context.mediaPlayer = MediaPlayer()
                    context.mediaPlayer?.setDataSource(context, musicList[position].uri!!)
                    context.mediaPlayer?.prepare()
                    if (context.isPaused == false) {
                        context.changePageWithoutCallBack(position)




                        context.mediaPlayer?.start()
                    }

                    while (context.mediaPlayer!!.isPlaying || isPaused == true) {
                        if (stopp == true) {
                            context.mediaPlayer?.stop()
                            context.mediaPlayer?.release()
                            context.mediaPlayer = null
                            return
                        }

                    }


                    position++


                }

            }


        }

    }

}