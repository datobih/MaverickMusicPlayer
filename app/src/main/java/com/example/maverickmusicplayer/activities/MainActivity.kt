package com.example.maverickmusicplayer.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.adapters.PagerFragmentAdapter
import com.example.maverickmusicplayer.fragments.PagerSongPlaying
import com.example.maverickmusicplayer.models.Music
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mediaPlayer:MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


      setSupportActionBar(toolbar_main)
        if(supportActionBar!=null){


            supportActionBar?.setDisplayHomeAsUpEnabled(true)


        }


        mediaPlayer=MediaPlayer()




        while (Constants.checkPermission(this) == false) {

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
    if(mediaPlayer!!.isPlaying){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
    }
    mediaPlayer= MediaPlayer()

        mediaPlayer?.setDataSource(this@MainActivity,(vp_songPlaying.adapter as PagerSongPlaying).musicList[position].uri!!)

        mediaPlayer?.prepare()
        mediaPlayer?.start()


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
    vp_songPlaying.adapter=adapter


}






        /*

        var musicList = deviceData.getMediaFromDevice()
        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()

        var songsRecyclerAdapter=SongsRecyclerAdapter(this,musicList)
        rv_songs.layoutManager=LinearLayoutManager(this)
        rv_songs.adapter=songsRecyclerAdapter




     */

    }
