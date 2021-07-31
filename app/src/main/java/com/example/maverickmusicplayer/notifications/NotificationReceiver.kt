package com.example.maverickmusicplayer.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.constants.Constants
import kotlinx.android.synthetic.main.activity_main.*

class NotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       var input=intent?.getIntExtra(Constants.PREVIOUS_EXTRA,-1)

        if(input==-1){
            input=intent?.getIntExtra(Constants.PAUSE_EXTRA,-1)
        }

        if(input==-1){
             input=intent?.getIntExtra(Constants.NEXT_EXTRA,-1)
        }


        if(input!=-1){
if(input==0){
    (Constants.mainActivity as MainActivity).vp_songPlaying.currentItem=  (Constants.mainActivity as MainActivity).vp_songPlaying.currentItem-1
}
            else if(input==1){
                (Constants.mainActivity as MainActivity).setPause()
            }

            else if(input==2){
    (Constants.mainActivity as MainActivity).vp_songPlaying.currentItem=  (Constants.mainActivity as MainActivity).vp_songPlaying.currentItem+1
            }


        }


    }
}