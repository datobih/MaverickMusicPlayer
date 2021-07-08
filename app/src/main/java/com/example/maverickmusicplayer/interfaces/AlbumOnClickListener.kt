package com.example.maverickmusicplayer.interfaces

import com.example.maverickmusicplayer.models.Album

interface AlbumOnClickListener {
    fun onItemClicked(album: Album)
}