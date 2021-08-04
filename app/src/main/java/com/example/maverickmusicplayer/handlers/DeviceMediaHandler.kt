package com.example.maverickmusicplayer.handlers

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.maverickmusicplayer.models.Album
import com.example.maverickmusicplayer.models.Artist
import com.example.maverickmusicplayer.models.Music

class DeviceMediaHandler(val context: Context) {

    val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

    val albumCollection =
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
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DISPLAY_NAME




            )

    val songSelection = "${MediaStore.Audio.Media.DISPLAY_NAME} LIKE ?"

    val selectionArgs = arrayOf("%mp3")



    val albumProjection = arrayOf(
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,


            )

    val artistProjection = arrayOf(
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS

            )

    val artistCollection=
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)

            } else {
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
            }


    fun getMediaFromDevice( album:String?,artist: String?): ArrayList<Music> {
        var query:Cursor?=null
var tempSelection=songSelection
        var tempSelectionArgs=selectionArgs
        if(!artist.isNullOrEmpty()){
            tempSelection=   "${MediaStore.Audio.Media.DISPLAY_NAME} LIKE ? AND ${MediaStore.Audio.Media.ARTIST} LIKE ?"
            tempSelectionArgs= arrayOf("%mp3",artist)



        }

        if(album!=null) {
            val albumSongSelection =
                "${MediaStore.Audio.Media.DISPLAY_NAME} LIKE ? AND ${MediaStore.Audio.Media.ALBUM} LIKE ?"
            val albumSelectionArgs = arrayOf("%mp3", album)


            query = context.contentResolver.query(collection, songProjection, albumSongSelection, albumSelectionArgs, null)

        }

        else {
            query = context.contentResolver.query(
                collection,
                songProjection,
               tempSelection,
                tempSelectionArgs,
                null
            )

        }
            val musicList = ArrayList<Music>()


        query.use { cursor ->


            var idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            var nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            var durationColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            var sizeColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            var artistColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            var dataColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            var albumColumn=cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)




            while (cursor!!.moveToNext()) {
                val id = cursor.getLong(idColumn!!)
                val name = cursor.getString(nameColumn!!)
                val duration = cursor.getInt(durationColumn!!)
                val size = cursor.getInt(sizeColumn!!)
                var artist = cursor.getString(artistColumn!!)
                val pathData = cursor.getString(dataColumn!!)
                val album=cursor.getString(albumColumn!!)

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
                    bitmap = null
                }


                /*
            val artworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(artworkUri, id)


             */



                musicList.add(Music(contentUri, id, name, artist,album, bitmap, duration, size))
            }








            return musicList
        }


    }


    fun getAlbums( artist:String?): ArrayList<Album> {
        var tempSelection:String?=null
        var tempSelectionArg:Array<String>?=null
         var albumArtUri: Uri?=null
        if(!artist.isNullOrEmpty()){
            tempSelection="${MediaStore.Audio.Albums.ARTIST} LIKE ?"
             tempSelectionArg=arrayOf(artist)

        }

        var query = context.contentResolver.query(albumCollection, albumProjection, tempSelection, tempSelectionArg, null)
        val albumList = ArrayList<Album>()
        var bitmap: Bitmap? = null

        query.use { cursor ->

            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            val nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
            val albumArtColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)
            val artistColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)
            val noOfSongsColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)



            while (cursor!!.moveToNext()) {

                val id = cursor.getLong(idColumn!!)
                val name = cursor.getString(nameColumn!!)
                val albumArt = cursor.getString(albumArtColumn!!)
                val artist = cursor.getString(artistColumn!!)
                val noOfSongs = cursor.getInt(noOfSongsColumn!!)

                var directoryArtUri=Uri.parse("content://media/external/audio/albumart")

                var albumArtUri = ContentUris.withAppendedId(directoryArtUri, id)







                albumList.add(Album(id, name, artist, albumArtUri, noOfSongs))

            }


        }
        return albumList
    }



    fun getArtists(): ArrayList<Artist> {

        var query = context.contentResolver.query(artistCollection, artistProjection, null, null, null)
        val artistList = ArrayList<Artist>()
        var bitmap: Bitmap? = null
        var first=true

        query.use { cursor ->

            val idColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            val nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
            val artistAlbumsColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
            val artistTracksColumn = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)







            while (cursor!!.moveToNext()) {

                val id = cursor.getLong(idColumn!!)
                val name = cursor.getString(nameColumn!!)

                val artistAlbums = cursor.getInt(artistAlbumsColumn!!)
                val artistTracks = cursor.getInt(artistTracksColumn!!)




//CAPTURE FIRST ALBUM ART
    val projection=arrayOf(MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ARTIST)
    val tempSelection="${MediaStore.Audio.Media.ARTIST} LIKE ?"
    val tempSelectionArgs= arrayOf(name)


    context.contentResolver.query(collection,projection,tempSelection,tempSelectionArgs,null).use {cursor1->

        val tempDataColumn=cursor1?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

        cursor1!!.moveToNext()
        val tempData=cursor1.getString(tempDataColumn!!)




        try {
            var mediaMetaDataRetriever = MediaMetadataRetriever()
            mediaMetaDataRetriever.setDataSource(tempData)
            val data = mediaMetaDataRetriever.embeddedPicture
            bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)

            mediaMetaDataRetriever.release()
        }

        catch (e:Exception){
            bitmap=null
        }


    }










                artistList.add(Artist(id, name, artistAlbums, artistTracks,bitmap))

            }


        }
        return artistList
    }


}