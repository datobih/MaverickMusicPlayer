package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.interfaces.AlbumOnClickListener
import com.example.maverickmusicplayer.models.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumRecyclerAdapter(val context: Context, val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

var albumOnClickListener:AlbumOnClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_album, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(albumList[position].art!=null){

            holder.albumArt.setImageBitmap(albumList[position].art)
        }

        else{
            var drawable=context.resources.getDrawable(R.drawable.album_placeholder)
            holder.albumArt.background=drawable
        }

        holder.albumTitle.text=albumList[position].name
        holder.albumArtist.text=albumList[position].artist

        holder.albumLayout.setOnClickListener {

            albumOnClickListener?.onItemClicked(albumList[position])

        }


    }

    override fun getItemCount(): Int {
        return albumList.size
    }





    fun setOnAlbumClicked( mAlbumOnClickListener: AlbumOnClickListener){

        this.albumOnClickListener=mAlbumOnClickListener

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var albumArt = view.imb_albumCover
        var albumTitle = view.tv_albumTitle
        val albumArtist = view.tv_albumArtist
        val albumLayout=view.layout_item_album

    }


}