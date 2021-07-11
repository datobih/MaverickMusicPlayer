package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.interfaces.SongOnClickListener
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_music.view.*
import java.lang.StringBuilder

class SongsRecyclerAdapter(val context: Context,val musicList:ArrayList<Music>):RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder>() {


var connected=false
var songOnClickListener:SongOnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_music,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var title=musicList[position].name
       var sb=StringBuilder()
        sb.append(title)

        sb.replace((sb.toString().lastIndex)-3,sb.toString().length,"")

        title=sb.toString()

        if(musicList[position].albumArt!=null){

            holder.songCover.setImageBitmap(musicList[position].albumArt)
        }

        else{
            var drawable=context.resources.getDrawable(R.drawable.songs_placeholder)
            holder.songCover.setImageDrawable(drawable)
        }
        holder.songTitle.text=title
        holder.songArtist.text=musicList[position].artist



        holder.songLayout.setOnClickListener {

            if(connected==false){
              if(context is MainActivity){
                  context.setPlayingAdapter(musicList)
                    context.vp_songPlaying.visibility=View.VISIBLE

                  connected=true
              }
            }



            if(connected==true){

                songOnClickListener!!.onItemClicked(position)




                if(context is MainActivity){
                    if(context.mediaPlayer!!.isPlaying){
                        context.mediaPlayer?.stop()
                        context.mediaPlayer?.release()
                        context.mediaPlayer=null
                    }
                    context.mediaPlayer= MediaPlayer()
                    context.mediaPlayer?.setDataSource(context,musicList[position].uri!!)
                    context.mediaPlayer?.prepare()
                    context.mediaPlayer?.start()

                }

            }




        }




    }

    override fun getItemCount(): Int {
      return musicList.size
    }

    fun setOnSongClicked(mSongOnClickListener: SongOnClickListener){
        this.songOnClickListener=mSongOnClickListener
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var songTitle=view.tv_songTitle
        var songCover=view.imv_songCover
        var songArtist=view.tv_songArtist
        var songLayout=view.ll_songItem


    }


}