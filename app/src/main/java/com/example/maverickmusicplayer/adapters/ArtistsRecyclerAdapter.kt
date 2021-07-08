package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.interfaces.ArtistOnClickListener
import com.example.maverickmusicplayer.models.Artist
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistsRecyclerAdapter(val context: Context, val artistList:ArrayList<Artist>):RecyclerView.Adapter<ArtistsRecyclerAdapter.ViewHolder>() {


var artistOnClickListener:ArtistOnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_artist,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var track="Tracks"
        var album="Albums"
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
        if(artistList[position].noOfAlbums<=1){
            album="Album"
        }

        holder.artistContentCount.text="${artistList[position].noOfTracks} ${track}| ${artistList[position].noOfAlbums} ${album}"

        holder.artistLayout.setOnClickListener {
        artistOnClickListener?.onItemClicked(artistList[position].name)


        }


    }

    override fun getItemCount(): Int {
      return artistList.size
    }

    fun setOnArtistClicked(mArtistOnClickListener: ArtistOnClickListener){
        this.artistOnClickListener=mArtistOnClickListener

    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var artistTitle=view.tv_artistTitle
        var artCover=view.imv_artistCover
        var artistContentCount=view.tv_artistContentCount
        var artistLayout=view.layout_item_artist

    }


}