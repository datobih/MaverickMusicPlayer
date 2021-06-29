package com.example.maverickmusicplayer.models

import android.graphics.Bitmap
import android.net.Uri

data class Album(
        //val uri: Uri,
        val id: Long,
        val name:String,
        val artist:String,
        val art:Bitmap,
        val numberOfSongs:Int,


        ) {
}