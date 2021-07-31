package com.example.maverickmusicplayer.activities

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadata
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.adapters.PagerFragmentAdapter
import com.example.maverickmusicplayer.adapters.PagerNowPlaying
import com.example.maverickmusicplayer.adapters.PagerSongPlaying
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.handlers.PlaybackThread
import com.example.maverickmusicplayer.interfaces.SongPlayingOnClickListener
import com.example.maverickmusicplayer.models.Music
import com.example.maverickmusicplayer.notifications.NotificationReceiver
import com.example.maverickmusicplayer.utils.BlurBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_album_song.*
import kotlinx.android.synthetic.main.fragment_artist_library.*
import kotlinx.android.synthetic.main.fragment_songs.*


class MainActivity : AppCompatActivity() {


    var playbackThread: PlaybackThread? = null
    var isPaused = false
    var change = true
    var check = false
    var isInit = true
    var test = false
    var repeat = 'p'
    var isShuffle = false
    var shufflePos = 0
    var testt=4
    var unshuffle = false
    var shuffledList: ArrayList<Music> = ArrayList<Music>()
    var permaMusicList: ArrayList<Music>? = null
    var notificationManager:NotificationManagerCompat?=null
    var notifPause=0
    var notification:Notification?=null
    var currentMusic:Music?=null
    var contentPendingIntent:PendingIntent?=null
    var pendingPrevious:PendingIntent?=null
    var pendingNext:PendingIntent?=null
    var pendingPause:PendingIntent?=null
    var mediaSession:MediaSessionCompat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

setSupportActionBar(toolbar_main)


        mediaSession= MediaSessionCompat(this,"tag")

        val mediaMetaData=MediaMetadata.Builder()
                .putLong(MediaMetadata.METADATA_KEY_DURATION,-1L).build()
        mediaSession!!.setMetadata(MediaMetadataCompat.fromMediaMetadata(mediaMetaData))


var contentIntent= Intent(this,this::class.java)

         contentPendingIntent=PendingIntent.getActivity(this,Constants.RQ_SKIP,contentIntent,0)



        var previousSkipIntent=Intent(this,NotificationReceiver::class.java)
        Constants.mainActivity=this
        previousSkipIntent.putExtra(Constants.PREVIOUS_EXTRA,Constants.PREVIOUS_VALUE)
         pendingPrevious=PendingIntent.getBroadcast(this,Constants.PREVIOUS_VALUE,previousSkipIntent,PendingIntent.FLAG_UPDATE_CURRENT)


        var nextSkipIntent=Intent(this,NotificationReceiver::class.java)

        nextSkipIntent.putExtra(Constants.NEXT_EXTRA,Constants.NEXT_VALUE)
         pendingNext=PendingIntent.getBroadcast(this,Constants.NEXT_VALUE,nextSkipIntent,PendingIntent.FLAG_UPDATE_CURRENT)


        var pauseIntent=Intent(this,NotificationReceiver::class.java)

        pauseIntent.putExtra(Constants.PAUSE_EXTRA,Constants.PAUSE_VALUE)
         pendingPause=PendingIntent.getBroadcast(this,Constants.PAUSE_VALUE,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT)


 notifPause=R.drawable.baseline_pause_24


        notificationManager= NotificationManagerCompat.from(this@MainActivity)




        while (Constants.checkPermission(this) == false) {

        }

        imb_closeNowPlaying.setOnClickListener {
            onBackPressed()
        }


        imb_pause_play.setOnClickListener {

            setPause()


        }

        imb_nowPlaying_pausePlay.setOnClickListener {
            setPause()
        }

        imb_nowPlaying_skipNext.setOnClickListener {

            vp_nowPlaying.currentItem = (vp_nowPlaying.currentItem) + 1

        }

        imb_nowPlaying_skipPrevious.setOnClickListener {
            vp_nowPlaying.currentItem = (vp_nowPlaying.currentItem) - 1
        }

        imb_nowPlaying_repeat.setOnClickListener {
            test = true
            if (repeat == 'p') {
                repeat = 'r'
                imb_nowPlaying_repeat.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_repeat_red_24)
            } else if (repeat == 'r') {
                repeat = 'R'
                imb_nowPlaying_repeat.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_repeat_one_24)
            } else if (repeat == 'R') {
                repeat = 'p'
                imb_nowPlaying_repeat.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_repeat_24)
            }


        }
        imb_nowPlaying_shuffle.setOnClickListener {

            check = true
            if (isShuffle == false && unshuffle == false) {
                unshuffle = true
                isShuffle = true
                shufflePos = vp_songPlaying.currentItem
                imb_nowPlaying_shuffle.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_shuffle_red_24)
                shuffledList.addAll(playbackThread!!.musicList!!)
                permaMusicList = playbackThread!!.musicList
                var tempMusic =
                    (vp_songPlaying.adapter as PagerSongPlaying).musicList[vp_songPlaying.currentItem]
                shuffledList?.shuffle()


                var playingPos = shuffledList.indexOf(tempMusic)

                //Swap Positions of current shuffled index with first
                shuffledList[playingPos] = shuffledList[0]
                shuffledList[0] = tempMusic


                setPlayingAdapter(shuffledList)
                vp_nowPlaying.adapter = PagerNowPlaying(this, shuffledList)

                change = false

                playbackThread!!.position = 0
                vp_nowPlaying.currentItem = 0



                playbackThread!!.musicList = shuffledList


            } else if (isShuffle == true || unshuffle == true) {

                imb_nowPlaying_shuffle.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_baseline_shuffle_24)
                isShuffle = false












                setPlayingAdapter(permaMusicList!!)
                vp_nowPlaying.adapter = PagerNowPlaying(this, permaMusicList!!)

                change = false

                playbackThread!!.position = shufflePos
                vp_nowPlaying.currentItem = shufflePos



                playbackThread!!.musicList = permaMusicList
                unshuffle = false
            }

            check = false

        }


        var adapter = PagerFragmentAdapter(supportFragmentManager, lifecycle)
        vp_main.adapter = adapter
        vp_main.isUserInputEnabled = false

        vp_main.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)


            }
        })




        vp_songPlaying.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                change = true

            }


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (change == true) {

 currentMusic=(vp_songPlaying.adapter as PagerSongPlaying).musicList[position]




                    if (isInit == true) {
                        playbackThread = PlaybackThread(
                            this@MainActivity
                        )
                        playbackThread!!.musicList =
                            (vp_songPlaying.adapter as PagerSongPlaying).musicList
                        playbackThread!!.position = position
                        playbackThread?.start()

                        isInit = false
                    } else {

                        playbackThread!!.reset = true


                        playbackThread!!.musicList =
                            (vp_songPlaying.adapter as PagerSongPlaying).musicList
                        playbackThread!!.position = position


                    }

                   startPlaybackService()


                    /*
                    if (isPaused == true) {
                        playbackThread?.isPaused = true
                    }

                     */

                } else {
                    isShuffle = false


                }

            }


        })

        sb_nowPlaying.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (ll_nowPlaying.visibility == View.VISIBLE) {
                    val currentDurationMillis = sb_nowPlaying.progress

                    val currentDurationMins = (currentDurationMillis / 1000) / 60
                    val currentDurationSecs = (currentDurationMillis / 1000) % 60
                    if (currentDurationSecs < 10) {
                        runOnUiThread {
                            tv_nowPlaying_currentDuration.text =
                                "${currentDurationMins.toString()}:0${currentDurationSecs.toString()}"
                        }
                    } else {
                        runOnUiThread {
                            tv_nowPlaying_currentDuration.text =
                                "${currentDurationMins.toString()}:${currentDurationSecs.toString()}"
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

                playbackThread?.onTap = true

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playbackThread?.mediaPlayer?.seekTo(sb_nowPlaying.progress)
                playbackThread?.onTap = false
            }


        })


        vp_nowPlaying.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //   change=true


            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (Constants.nowPlayingParent == true) {
                    tv_nowPlaying_parentTitle.text =
                        (vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].album
                } else {
                    tv_nowPlaying_parentTitle.text = "songs"
                }
                tv_nowPlaying_songTitle.text =
                    (vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].name
                tv_nowPlaying_songArtist.text =
                    (vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].artist
                val totalDurationMillis =
                    (vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].duration
                var totalDurationMins = (totalDurationMillis / 1000) / 60
                var totalDurationSecs = ((totalDurationMillis / 1000) % 60)

                if (totalDurationSecs < 10) {
                    tv_nowPlaying_totalDuration.text =
                        "${totalDurationMins.toString()}:0${totalDurationSecs.toString()}"
                } else {
                    tv_nowPlaying_totalDuration.text =
                        "${totalDurationMins.toString()}:${totalDurationSecs.toString()}"
                }

                var sampleArt =
                    (vp_nowPlaying.adapter as PagerNowPlaying).musicList[position].albumArt

                if (sampleArt != null) {
                    sampleArt = BlurBuilder.blur(this@MainActivity, sampleArt)
                    sampleArt = darkenBitMap(sampleArt)
                    var bitmapDrawable = BitmapDrawable(sampleArt)
                    ll_nowPlaying.background = bitmapDrawable


                } else {
                    ll_nowPlaying.background = ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.now_playing_theme
                    )
                }

                vp_songPlaying.setCurrentItem(position, false)

            }

        })


        bottom_navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {


                when (item.itemId) {

                    R.id.nav_songs -> {

                        vp_main.currentItem = 0
                        return true
                    }

                    R.id.nav_albums -> {

                        vp_main.currentItem = 1
                        return true

                    }

                    R.id.nav_artists -> {
                        vp_main.currentItem = 2
                        return true
                    }

                }
                return false
            }


        })

        vp_main.currentItem = 1
        vp_main.currentItem = 0

    }

    fun setPlayingAdapter(musicList: ArrayList<Music>) {


        var adapter = PagerSongPlaying(this, musicList)

        adapter.setSongPlayingOnClickListener(object : SongPlayingOnClickListener {
            override fun onItemClicked(position: Int) {
                ll_main.visibility = View.INVISIBLE
               toolbar_main.visibility=View.GONE
                ll_nowPlaying.visibility = View.VISIBLE

                var adapter = PagerNowPlaying(
                    this@MainActivity,
                    (vp_songPlaying.adapter as PagerSongPlaying).musicList
                )
                vp_nowPlaying.adapter = adapter
                vp_nowPlaying.setCurrentItem(position, false)


            }


        })

        vp_songPlaying.adapter = adapter


    }

    fun setPause() {

        if (isPaused == false) {
            imb_pause_play.background = ContextCompat.getDrawable(
                this,
                R.drawable.baseline_play_arrow_24
            )
            imb_nowPlaying_pausePlay.background = ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_play_circle_filled_24
            )
notifPause=R.drawable.baseline_play_arrow_24
            isPaused = true
            playbackThread?.isPaused = true
            playbackThread?.mediaPlayer!!.pause()


        } else {
            imb_pause_play.background = ContextCompat.getDrawable(
                this,
                R.drawable.baseline_pause_24
            )
            imb_nowPlaying_pausePlay.background = ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_pause_circle_filled_24
            )
            isPaused = false
            playbackThread?.mediaPlayer!!.start()
            playbackThread?.isPaused = false
notifPause=R.drawable.baseline_pause_24

        }



       startPlaybackService()

    }

    override fun onBackPressed() {

        if (ll_nowPlaying.visibility == View.VISIBLE) {
            toolbar_main.visibility=View.VISIBLE
            ll_main.visibility = View.VISIBLE
            ll_nowPlaying.visibility = View.GONE

        } else {

if(supportFragmentManager.backStackEntryCount==0){
    if(vp_main.currentItem!=0){

   bottom_navigation.selectedItemId=R.id.nav_songs
    }

}
else{
    super.onBackPressed()
}


        }


        /*

        var musicList = deviceData.getMediaFromDevice()
        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()

        var songsRecyclerAdapter=SongsRecyclerAdapter(this,musicList)
        rv_songs.layoutManager=LinearLayoutManager(this)
        rv_songs.adapter=songsRecyclerAdapter




     */

    }

    fun updateMusicProgress() {
        var handler = Handler(Looper.getMainLooper())
        handler.post {
            sb_nowPlaying.progress = playbackThread?.mediaPlayer!!.currentPosition

        }
    }

    fun changePageWithoutCallBack(position: Int) {
        change = false
        var handler = Handler(Looper.getMainLooper())
        handler.post {
            vp_nowPlaying.currentItem = position
            vp_songPlaying.currentItem = position
        }


        change = true


    }


    private fun darkenBitMap(bm: Bitmap): Bitmap? {
        val canvas = Canvas(bm)
        canvas.drawARGB(100, 0, 0, 0)
        canvas.drawBitmap(bm, Matrix(), Paint())
        return bm
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.action_bar_menu,menu)

       val menuItem=menu?.findItem(R.id.search_barr)
        val searchView=menuItem?.actionView as androidx.appcompat.widget.SearchView
        searchView.imeOptions=EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                Constants.songsRecyclerAdapter?.exampleFilter?.filter(newText)
return false
            }

        })

        return super.onCreateOptionsMenu(menu)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.search_barr){
            bottom_navigation.selectedItemId=R.id.nav_songs

        }

        return super.onOptionsItemSelected(item)
    }

    fun startPlaybackService(){

        var serviceIntent=Intent(this@MainActivity,PlaybackService::class.java)

        startService(serviceIntent)

    }

    fun stopPlayBackService(){
        var serviceIntent=Intent(this@MainActivity,PlaybackService::class.java)
serviceIntent.setAction("end")
        startService(serviceIntent)

    }

    override fun onDestroy() {
        playbackThread?.endThread = true


        playbackThread=null

        stopPlayBackService()
        super.onDestroy()


    }

}
