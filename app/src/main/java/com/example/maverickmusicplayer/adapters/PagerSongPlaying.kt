package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.interfaces.SongPlayingOnClickListener
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.fragment_songs.*
import kotlinx.android.synthetic.main.item_song_playing.view.*
import java.lang.StringBuilder

class PagerSongPlaying(val context:Context,val musicList:ArrayList<Music>):RecyclerView.Adapter<PagerSongPlaying.ViewHolder>() {

var songPlayingOnClickListener:SongPlayingOnClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_song_playing,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var title=musicList[position].name
        var sb= StringBuilder()
        sb.append(title)

        sb.replace((sb.toString().lastIndex)-3,sb.toString().length,"")

        title=sb.toString()

        holder.songCover.setImageBitmap(musicList[position].albumArt)
        holder.songTitle.text=title
        holder.songArtist.text=musicList[position].artist




holder.songTitle.isSelected=true

        holder.songLayout.setOnClickListener {
            if (context is MainActivity) {

                songPlayingOnClickListener?.onItemClicked(position)

            }
        }

    }

    override fun getItemCount(): Int {
       return musicList.size
    }



    @JvmName("setSongPlayingOnClickListener1")
    fun setSongPlayingOnClickListener(mSongPlayingOnClickListener: SongPlayingOnClickListener){
        this.songPlayingOnClickListener=mSongPlayingOnClickListener
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var songCover=view.imv_playing_songCover
        var songTitle=view.tv_playing_songTitle
        val songArtist=view.tv_playing_artist
        var songLayout=view.ll_item_songPlaying
    }


}