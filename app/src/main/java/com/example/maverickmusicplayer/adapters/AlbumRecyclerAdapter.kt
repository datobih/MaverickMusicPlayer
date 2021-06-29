package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.models.Album
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_music.view.*

class AlbumRecyclerAdapter(val context: Context, val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_album, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.albumArt.setImageBitmap(albumList[position].art)
        holder.albumTitle.text=albumList[position].name
        holder.albumArtist.text=albumList[position].artist

    }

    override fun getItemCount(): Int {
        return albumList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var albumArt = view.imb_albumCover
        var albumTitle = view.tv_albumTitle
        val albumArtist = view.tv_albumArtist

    }


}