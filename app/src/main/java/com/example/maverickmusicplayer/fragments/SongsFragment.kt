package com.example.maverickmusicplayer.fragments

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.adapters.SongsRecyclerAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.handlers.DeviceMediaHandler
import com.example.maverickmusicplayer.interfaces.SongOnClickListener
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_songs.*


class SongsFragment : Fragment() {


    var artist:String?=null
    var musicList=ArrayList<Music>()
    var task:AsyncTask<Void,Void,ArrayList<Music>>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var bundle=this.arguments
       artist=bundle?.getString(Constants.ARTIST_TRACKS_BUNDLE)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

val dialog:Dialog= Dialog(requireContext())
        task=object :AsyncTask<Void,Void,ArrayList<Music>>(){

            override fun onPreExecute() {
                super.onPreExecute()
            ll_songLoading.visibility=View.VISIBLE
            rv_songs.visibility=View.GONE
            tv_songHeader.visibility=View.GONE


            }

           override fun doInBackground(vararg params: Void?): ArrayList<Music> {


               var songs = DeviceMediaHandler(requireContext()).getMediaFromDevice(null,artist)

               return songs
           }

           override fun onPostExecute(result: ArrayList<Music>?) {
               super.onPostExecute(result)
               musicList=result!!

               Constants.dismissDialog(dialog)

               refreshSongs()
               ll_songLoading.visibility=View.GONE
               rv_songs.visibility=View.VISIBLE
                tv_songHeader.visibility=View.VISIBLE
           }


       }.execute()




        //Toast.makeText(this, musicList[0].name, Toast.LENGTH_LONG).show()




    }
    fun refreshSongs(){
        var songsRecyclerAdapter:SongsRecyclerAdapter?=null

        if(artist==null) {
            songsRecyclerAdapter = SongsRecyclerAdapter(requireContext(), musicList, false)


        }
        else{
            songsRecyclerAdapter = SongsRecyclerAdapter(requireContext(), musicList, true)
        }

            rv_songs.layoutManager = LinearLayoutManager(requireContext())
        rv_songs.adapter = songsRecyclerAdapter
        Constants.songsRecyclerAdapter=songsRecyclerAdapter

        songsRecyclerAdapter.setOnSongClicked(object:SongOnClickListener{
            override fun onItemClicked(position: Int) {
                Constants.nowPlayingParent=songsRecyclerAdapter.parent

                (activity as MainActivity).vp_songPlaying.setCurrentItem(position,false)


                if(Constants.nowPlayingParent==true) {
                    (activity as MainActivity).tv_nowPlaying_parentTitle.text = musicList[position].album
                }
                else{
                    (activity as MainActivity).tv_nowPlaying_parentTitle.text="songs"
                }
                (activity as MainActivity).tv_nowPlaying_songTitle.text=musicList[position].name
                (activity as MainActivity).tv_nowPlaying_songTitle.isSelected=true
                (activity as MainActivity).tv_nowPlaying_songArtist.text=musicList[position].artist
                (activity as MainActivity).permaMusicList=musicList

            }


        })

    }



    override fun onDestroy() {
        super.onDestroy()
        task!!.cancel(true)
        task=null
    }




}



