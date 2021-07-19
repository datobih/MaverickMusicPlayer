package com.example.maverickmusicplayer.models

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.ValueIterator
import android.media.MediaPlayer
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import com.example.maverickmusicplayer.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Handler

class PlaybackThread(val context: Context) : Thread() {
    var mediaPlayer: MediaPlayer? = null
    public var stopp = false
    public var isPaused = false
    var musicList: ArrayList<Music>? = null
    var position: Int? = null

    var reset = false
    var onTap = false

    @Synchronized
    override fun run() {


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
                            context.changePageWithoutCallBack(position!!)




                            mediaPlayer?.start()
                            context.sb_nowPlaying.max = mediaPlayer!!.duration


                        }

                        while (mediaPlayer!!.isPlaying || isPaused == true) {
                            @Synchronized
                            if (onTap == false && hack % 300 == 0) {
                                context.sb_nowPlaying.progress = mediaPlayer!!.currentPosition


                            }

                            if (reset == true) {
                                stopp = true
                                break
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
                                    position = position!! - 1
                                    if (position == musicList?.lastIndex) {
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