package com.example.maverickmusicplayer.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.handlers.DeviceMediaHandler
import com.example.maverickmusicplayer.adapters.SongsRecyclerAdapter
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.fragment_songs.*


class SongsFragment : Fragment() {



    var musicList=ArrayList<Music>()
    var task:AsyncTask<Void,Void,ArrayList<Music>>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task=object :AsyncTask<Void,Void,ArrayList<Music>>(){
           override fun doInBackground(vararg params: Void?): ArrayList<Music> {
               var songs = DeviceMediaHandler(requireContext()).getMediaFromDevice()

               return songs
           }

           override fun onPostExecute(result: ArrayList<Music>?) {
               super.onPostExecute(result)
               musicList=result!!
                refreshSongs()


           }


       }.execute()

        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()




    }
    fun refreshSongs(){
        var songsRecyclerAdapter = SongsRecyclerAdapter(requireContext(), musicList)
        rv_songs.layoutManager = LinearLayoutManager(requireContext())
        rv_songs.adapter = songsRecyclerAdapter
    }


    override fun onDestroy() {
        super.onDestroy()
        task!!.cancel(true)
        task=null
    }


    }



