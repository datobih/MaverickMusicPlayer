package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.item_music.view.*
import java.lang.StringBuilder

class SongsRecyclerAdapter(val context: Context,val musicList:ArrayList<Music>):RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder>() {




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



    }

    override fun getItemCount(): Int {
      return musicList.size
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var songTitle=view.tv_songTitle
        var songCover=view.imv_songCover
        var songArtist=view.tv_songArtist


    }


}