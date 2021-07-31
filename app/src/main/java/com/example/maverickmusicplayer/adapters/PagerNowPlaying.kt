package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.item_now_playing.view.*

class PagerNowPlaying(val context: Context, val musicList:ArrayList<Music>):RecyclerView.Adapter<PagerNowPlaying.ViewHolder>()  {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerNowPlaying.ViewHolder {
      return PagerNowPlaying.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_now_playing,parent,false))
    }

    override fun onBindViewHolder(holder: PagerNowPlaying.ViewHolder, position: Int) {
        if(musicList[position].albumArt!=null) {
            holder.nowPlayingCover.setImageBitmap(musicList[position].albumArt)
        }



    }

    override fun getItemCount(): Int {
        return musicList.size
    }



    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var nowPlayingCover=view.imv_nowPlaying_cover

    }


}