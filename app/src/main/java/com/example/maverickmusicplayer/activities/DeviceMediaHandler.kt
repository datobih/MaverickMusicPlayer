 package com.example.maverickmusicplayer.activities

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.models.Album
import com.example.maverickmusicplayer.models.Music
import java.lang.Exception

class DeviceMediaHandler(val context: Context) {

    val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

    val albumCollection=
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)

            } else {
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
            }

    val songProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,


            )

    val songSelection="${MediaStore.Audio.Media.DISPLAY_NAME} LIKE ?"

    val selectionArgs= arrayOf("%mp3")


    val albumProjection = arrayOf(
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,


    )


    fun getMediaFromDevice(): ArrayList<Music> {

        val query = context.contentResolver.query(collection, songProjection, songSelection, selectionArgs, null)
        val musicList = ArrayList<Music>()


        query.use { cursor ->


            var idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            var nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            var durationColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            var sizeColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            var artistColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            var dataColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)




            while (cursor!!.moveToNext()) {
                val id = cursor.getLong(idColumn!!)
                val name = cursor.getString(nameColumn!!)
                val duration = cursor.getInt(durationColumn!!)
                val size = cursor.getInt(sizeColumn!!)
                var artist = cursor.getString(artistColumn!!)
                val pathData = cursor.getString(dataColumn!!)
                val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                )


                var bitmap: Bitmap? = null

                try {
                    var mediaMetaDataRetriever = MediaMetadataRetriever()
                    mediaMetaDataRetriever.setDataSource(pathData)
                    val data = mediaMetaDataRetriever.embeddedPicture
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)

                    mediaMetaDataRetriever.release()
                } catch (e: Exception) {
                    var drawable = context.getDrawable(R.drawable.gitt2)

                    bitmap = (drawable as BitmapDrawable).bitmap
                }



                /*
            val artworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(artworkUri, id)


             */



                musicList.add(Music(contentUri, id, name, artist, bitmap, duration, size))
            }








            return musicList
        }


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getAlbums():ArrayList<Album>{

        var query=context.contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,albumProjection,null,null,null)
        val albumList=ArrayList<Album>()
lateinit var bitmap: Bitmap

        query.use { cursor ->

        val idColumn=cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
        val nameColumn=cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
        val albumArtColumn=cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)
        val artistColumn=cursor?.getColumnIndexOrThrow( MediaStore.Audio.Albums.ARTIST)
        val noOfSongsColumn=cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)



            while (cursor!!.moveToNext()){

                val id=cursor.getLong(idColumn!!)
                val name=cursor.getString(nameColumn!!)
                val albumArt=cursor.getString(albumArtColumn!!)
                val artist=cursor.getString(artistColumn!!)
                val noOfSongs=cursor.getInt(noOfSongsColumn!!)


            var albumArtUri=ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,id)

                try {
                     bitmap = context.contentResolver.loadThumbnail(albumArtUri, Size(1024, 1024), null)
                }

                catch (e:Exception){
                    var drawable = context.getDrawable(R.drawable.gitt2)

                    bitmap = (drawable as BitmapDrawable).bitmap
                }





                albumList.add(Album(id,name,artist,bitmap,noOfSongs))

            }


        }
        return albumList
    }


}