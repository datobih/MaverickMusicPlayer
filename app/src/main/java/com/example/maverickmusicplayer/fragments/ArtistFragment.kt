package com.example.maverickmusicplayer.fragments

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.adapters.ArtistsRecyclerAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.handlers.DeviceMediaHandler
import com.example.maverickmusicplayer.interfaces.ArtistOnClickListener
import com.example.maverickmusicplayer.models.Artist
import kotlinx.android.synthetic.main.fragment_artist.*


class ArtistFragment : Fragment() {
    var task:AsyncTask<Void,Void,ArrayList<Artist>>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task=object : AsyncTask<Void, Void, ArrayList<Artist>>(){


            override fun onPreExecute() {

                ll_artistLoading.visibility=View.VISIBLE
                super.onPreExecute()
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun doInBackground(vararg params: Void?): ArrayList<Artist> {
                var artists = DeviceMediaHandler(requireContext()).getArtists()

                return artists
            }

            override fun onPostExecute(result: ArrayList<Artist>?) {
                super.onPostExecute(result)
                ll_artistLoading.visibility=View.GONE
                var adapter= ArtistsRecyclerAdapter(requireContext(),result!!)
                rv_artists.layoutManager= LinearLayoutManager(requireContext())
                adapter.setOnArtistClicked(object:ArtistOnClickListener{
                    override fun onItemClicked(artist: String) {
                        var bundle=Bundle()
                        bundle.putString(Constants.ARTIST_NAME_BUNDLE,artist)
                        val artistLibrary=ArtistLibraryFragment()
                        artistLibrary.arguments=bundle


                        requireActivity().supportFragmentManager?.beginTransaction()?.apply {
                            addToBackStack(null)
                            replace(R.id.holder_artist_fragment,artistLibrary)

                            commit() }
                    }

                })

                rv_artists.adapter=adapter


            }


        }.execute()


    }


}