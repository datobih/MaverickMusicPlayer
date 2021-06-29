package com.example.maverickmusicplayer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.adapters.Constants
import com.example.maverickmusicplayer.adapters.PagerFragmentAdapter
import com.example.maverickmusicplayer.fragments.AlbumsFragment
import com.example.maverickmusicplayer.fragments.SongsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_music.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)




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

                }
                return false
            }


        })

    }




        /*

        var musicList = deviceData.getMediaFromDevice()
        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()

        var songsRecyclerAdapter=SongsRecyclerAdapter(this,musicList)
        rv_songs.layoutManager=LinearLayoutManager(this)
        rv_songs.adapter=songsRecyclerAdapter




     */

    }
