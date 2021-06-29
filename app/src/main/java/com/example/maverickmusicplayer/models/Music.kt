package com.example.maverickmusicplayer.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

 class Music(
         val uri: Uri?,
         val id:Long,
         val name: String?,
         val artist: String?,
         val albumArt:Bitmap?,
         val duration: Int,
         val size: Int
) :Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(uri, flags)
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(artist)
        parcel.writeParcelable(albumArt, flags)
        parcel.writeInt(duration)
        parcel.writeInt(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Music> {
        override fun createFromParcel(parcel: Parcel): Music {
            return Music(parcel)
        }

        override fun newArray(size: Int): Array<Music?> {
            return arrayOfNulls(size)
        }
    }
}