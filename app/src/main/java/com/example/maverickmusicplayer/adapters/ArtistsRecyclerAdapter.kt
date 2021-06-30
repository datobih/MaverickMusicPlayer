package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.models.Artist
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.item_artist.view.*
import kotlinx.android.synthetic.main.item_music.view.*

class ArtistsRecyclerAdapter(val context: Context, val artistList:ArrayList<Artist>):RecyclerView.Adapter<ArtistsRecyclerAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_artist,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var track="Tracks"
        var title=artistList[position].name


        if(artistList[position].artistArt!=null){

            holder.artCover.setImageBitmap(artistList[position].artistArt)
        }

        else{
            var drawable=context.resources.getDrawable(R.drawable.songs_placeholder)
            holder.artCover.setImageDrawable(drawable)
        }
        holder.artistTitle.text=title


        if(artistList[position].noOfTracks<=1){
            track="Track"
        }

        holder.songArtist.text="${artistList[position].noOfTracks} ${track}| ${artistList[position].noOfAlbums} Albums"



    }

    override fun getItemCount(): Int {
      return artistList.size
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var artistTitle=view.tv_artistTitle
        var artCover=view.imv_artistCover
        var songArtist=view.tv_artistContentCount


    }


}