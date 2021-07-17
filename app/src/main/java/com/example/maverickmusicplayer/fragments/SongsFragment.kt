package com.example.maverickmusicplayer.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.adapters.AlbumRecyclerAdapter
import com.example.maverickmusicplayer.adapters.PagerFragmentAdapter
import com.example.maverickmusicplayer.handlers.DeviceMediaHandler
import com.example.maverickmusicplayer.adapters.SongsRecyclerAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.interfaces.AlbumOnClickListener
import com.example.maverickmusicplayer.interfaces.SongOnClickListener
import com.example.maverickmusicplayer.models.Album
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_albums.*
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
        var songsRecyclerAdapter = SongsRecyclerAdapter(requireContext(), musicList)
        rv_songs.layoutManager = LinearLayoutManager(requireContext())
        rv_songs.adapter = songsRecyclerAdapter

        songsRecyclerAdapter.setOnSongClicked(object:SongOnClickListener{
            override fun onItemClicked(position: Int) {

                (rv_songs.adapter as SongsRecyclerAdapter).notifyDataSetChanged()
                (activity as MainActivity).vp_songPlaying.setCurrentItem(position,false)



                (activity as MainActivity).tv_nowPlaying_songTitle.text=musicList[position].name
                (activity as MainActivity).tv_nowPlaying_songTitle.isSelected=true
                (activity as MainActivity).tv_nowPlaying_songArtist.text=musicList[position].artist


            }


        })

    }


    override fun onDestroy() {
        super.onDestroy()
        task!!.cancel(true)
        task=null
    }




    }



