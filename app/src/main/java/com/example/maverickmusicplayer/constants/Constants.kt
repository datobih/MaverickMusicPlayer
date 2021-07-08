package com.example.maverickmusicplayer.constants

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.maverickmusicplayer.R

object Constants {
 val ALBUM_SONG_BUNDLE="album_song_bundle"
 val ARTIST_ALBUM_BUNDLE="artist_bundle"
    val ARTIST_TRACKS_BUNDLE="artist_tracks_bundle"
    val ARTIST_NAME_BUNDLE="artist_name_bundle"


    fun displayDialog(dialog:Dialog){

        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.show()




    }


    fun dismissDialog(dialog: Dialog){
        dialog.dismiss()


    }


    fun checkPermission(context: Context):Boolean{
        var check=false
        if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if( ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
                var intent= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                var uri= Uri.fromParts("package",context.packageName,null)
                context.startActivity(intent)

            }


            else{
                ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }

        }

        else{
            check=true
        }

        return check
    }

}