package com.example.maverickmusicplayer.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Album(
        //val uri: Uri,
        val id: Long,
        val name:String?,
        val artist:String?,
        val art:Bitmap?,
        val numberOfSongs:Int,


        ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readLong(),
                parcel.readString(),
                parcel.readString(),
                parcel.readParcelable(Bitmap::class.java.classLoader),
                parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeLong(id)
                parcel.writeString(name)
                parcel.writeString(artist)
                parcel.writeParcelable(art, flags)
                parcel.writeInt(numberOfSongs)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Album> {
                override fun createFromParcel(parcel: Parcel): Album {
                        return Album(parcel)
                }

                override fun newArray(size: Int): Array<Album?> {
                        return arrayOfNulls(size)
                }
        }
}