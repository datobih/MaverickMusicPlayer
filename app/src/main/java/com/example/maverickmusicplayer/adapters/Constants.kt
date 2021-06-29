package com.example.maverickmusicplayer.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Constants {

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