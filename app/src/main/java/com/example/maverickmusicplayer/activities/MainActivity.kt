package com.example.maverickmusicplayer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.adapters.PagerFragmentAdapter
import com.example.maverickmusicplayer.adapters.PagerNowPlaying
import com.example.maverickmusicplayer.adapters.PagerSongPlaying
import com.example.maverickmusicplayer.interfaces.SongPlayingOnClickListener
import com.example.maverickmusicplayer.models.Music
import com.example.maverickmusicplayer.models.PlaybackThread
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mediaPlayer:MediaPlayer?=null
    var playbackThread:PlaybackThread?=null
    var isPaused=false
    var change=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)





        mediaPlayer=MediaPlayer()




        while (Constants.checkPermission(this) == false) {

        }

imb_closeNowPlaying.setOnClickListener {
    onBackPressed()
}
        imb_pause_play.setOnClickListener {

            if(isPaused==false){
                imb_pause_play.background=ContextCompat.getDrawable(this,R.drawable.baseline_play_arrow_24)
                isPaused=true
                playbackThread?.isPaused=true
                mediaPlayer!!.pause()


            }

            else{
                imb_pause_play.background=ContextCompat.getDrawable(this,R.drawable.baseline_pause_24)
                isPaused=false
                mediaPlayer!!.start()
                playbackThread?.isPaused=false








            }



        }


        var adapter=PagerFragmentAdapter(supportFragmentManager,lifecycle)
        vp_main.adapter=adapter
        vp_main.isUserInputEnabled=false

        vp_main.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)


            }
        })




vp_songPlaying.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){




    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)


    }


    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        if(change==true) {
            if (playbackThread != null) {

                playbackThread?.interrupt()
                playbackThread?.stopp = true

                while(playbackThread!!.isAlive){

                }
                playbackThread = null

            }
            playbackThread = PlaybackThread(this@MainActivity, (vp_songPlaying.adapter as PagerSongPlaying).musicList, position)

            playbackThread?.start()

            /*
            if (isPaused == true) {
                playbackThread?.isPaused = true
            }

             */

        }



    }



})


vp_nowPlaying.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)

        tv_nowPlaying_songTitle.text=(vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].name
        tv_nowPlaying_songArtist.text=(vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].artist
        vp_songPlaying.currentItem=position
    }

})


        bottom_navigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {


                when (item.itemId) {

                    R.id.nav_songs -> {

                    vp_main.currentItem=0
                        return true
                    }

                    R.id.nav_albums -> {

                        vp_main.currentItem=1
                        return true

                    }

                    R.id.nav_artists->{
                        vp_main.currentItem=2
                        return true
                    }

                }
                return false
            }


        })

        vp_main.currentItem=1
        vp_main.currentItem=0

    }

fun setPlayingAdapter(musicList:ArrayList<Music>){



    var adapter= PagerSongPlaying(this,musicList)

    adapter.setSongPlayingOnClickListener(object :SongPlayingOnClickListener{
        override fun onItemClicked(position: Int) {
            ll_nowPlaying.visibility= View.VISIBLE
            ll_main.visibility=View.GONE
            var adapter=PagerNowPlaying(this@MainActivity,(vp_songPlaying.adapter as PagerSongPlaying).musicList)
            vp_nowPlaying.adapter=adapter
            vp_nowPlaying.setCurrentItem(position,false)

        }


    })
    vp_songPlaying.adapter=adapter


}

    override fun onBackPressed() {

        if(ll_nowPlaying.visibility==View.VISIBLE){
            ll_main.visibility=View.VISIBLE
            ll_nowPlaying.visibility=View.GONE

        }
        else {
            super.onBackPressed()

        }






        /*

        var musicList = deviceData.getMediaFromDevice()
        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()

        var songsRecyclerAdapter=SongsRecyclerAdapter(this,musicList)
        rv_songs.layoutManager=LinearLayoutManager(this)
        rv_songs.adapter=songsRecyclerAdapter




     */

    }
    fun changePageWithoutCallBack(position:Int){
        change=false
        var handler=Handler(Looper.getMainLooper())
        handler.post {
            vp_nowPlaying.currentItem=position
            vp_songPlaying.currentItem=position
        }


        change=true


    }
    fun initMediaPlayer(musicList: ArrayList<Music>,position: Int){

        val handler=Handler(Looper.getMainLooper())
        handler.post {
if(mediaPlayer!=null) {
    mediaPlayer?.stop()
    mediaPlayer?.release()
    mediaPlayer = null
}

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(this, musicList[position].uri!!)
            mediaPlayer?.prepare()
            if(isPaused==false){
                changePageWithoutCallBack(position)




                mediaPlayer?.start()
            }
        }

    }



}
