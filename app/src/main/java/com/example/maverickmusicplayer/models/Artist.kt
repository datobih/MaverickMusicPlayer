package com.example.maverickmusicplayer.models

import android.graphics.Bitmap

data class Artist(val id:Long,
                  val name: String,
                  val noOfAlbums: Int,
                  val noOfTracks:Int,
                  val artistArt:Bitmap?) {

}