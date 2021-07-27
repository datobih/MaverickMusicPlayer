package com.example.maverickmusicplayer.handlers

import android.content.Context
import android.media.MediaPlayer
import android.view.View
import androidx.core.content.ContextCompat
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.activity_main.*

class PlaybackThread(val context: Context) : Thread() {
    var mediaPlayer: MediaPlayer? = null
    public var stopp = false
    public var isPaused = false
    var musicList: ArrayList<Music>? = null
    var position: Int? = null

    var reset = false
    var onTap = false


    override fun run() {
android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO)

        while (true) {
            reset = false
            if (musicList != null) {
                if (position!! <= musicList!!.lastIndex) {

                    if (context is MainActivity) {

                        if (context.isPaused == true) {
                            isPaused = true
                        }


                        if (mediaPlayer != null) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()

                            mediaPlayer = null
                        }

                        mediaPlayer = MediaPlayer()
                        mediaPlayer?.setDataSource(context, musicList!![position!!].uri!!)
                        mediaPlayer?.prepare()
                        var hack = 1
                        if (context.isPaused == false) {
                            if(context.ll_nowPlaying.visibility==View.VISIBLE) {
                                context.runOnUiThread { context.changePageWithoutCallBack(position!!) }
                            }

/*
if(context.unshuffle==false) {
    context.isShuffle = false

    context.imb_nowPlaying_shuffle.background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_shuffle_24)
}
(
 */
                            context.isShuffle = false

                            context.imb_nowPlaying_shuffle.background = ContextCompat.getDrawable(context, R.drawable.ic_baseline_shuffle_24)
    mediaPlayer?.start()
                            context.sb_nowPlaying.max = mediaPlayer!!.duration


                        }

                        while (mediaPlayer!!.isPlaying || isPaused == true) {

                            if (onTap == false && hack % 300 == 0) {
                                if(context.ll_nowPlaying.visibility==View.VISIBLE) {
                                     context.sb_nowPlaying.progress = mediaPlayer!!.currentPosition
                                }

                                if (reset == true) {
                                    stopp = true
                                    break
                                }
                            }





                            hack++

                        }

                        if (stopp == true) {
                            stopp = false
                            continue
                        }

                        if (position != null) {

                            if (context.repeat != 'R') {
                                position = position!! + 1

                                if (context.repeat == 'r') {

                                    if (position!!-1 == musicList?.lastIndex) {
                                        position = 0
                                    }
                                }

                            }


                        }

                    }

                }


            }
        }
    }

}