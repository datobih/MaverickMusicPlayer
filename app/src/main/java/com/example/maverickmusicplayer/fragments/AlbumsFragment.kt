package com.example.maverickmusicplayer.fragments

import android.app.Dialog
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.handlers.DeviceMediaHandler
import com.example.maverickmusicplayer.adapters.AlbumRecyclerAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.interfaces.AlbumOnClickListener
import com.example.maverickmusicplayer.models.Album
import kotlinx.android.synthetic.main.fragment_albums.*
import kotlinx.android.synthetic.main.fragment_artist.*
import kotlinx.android.synthetic.main.fragment_songs.*


class AlbumsFragment : Fragment() {
    var artist:String?=null
    var task: AsyncTask<Void, Void, ArrayList<Album>>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle=this.arguments
        artist=bundle?.getString(Constants.ARTIST_ALBUM_BUNDLE)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog: Dialog = Dialog(requireContext())
        task = object : AsyncTask<Void, Void, ArrayList<Album>>() {


            override fun onPreExecute() {
                super.onPreExecute()
                ll_albumLoading.visibility=View.VISIBLE
                rv_albums.visibility=View.GONE



            }

            override fun doInBackground(vararg params: Void?): ArrayList<Album> {
            var albums=ArrayList<Album>()
                if(artist.isNullOrEmpty()) {
                     albums = DeviceMediaHandler(requireContext()).getAlbums(null)
                }

                else{
                    albums = DeviceMediaHandler(requireContext()).getAlbums(artist)
                }
                return albums
            }

            override fun onPostExecute(result: ArrayList<Album>?) {
                super.onPostExecute(result)
                var adapter = AlbumRecyclerAdapter(requireContext(), result!!)

                adapter.setOnAlbumClicked(object : AlbumOnClickListener {
                    override fun onItemClicked(album: Album) {
                        var bundle=Bundle()
                        bundle.putParcelable(Constants.ALBUM_SONG_BUNDLE,album)
                        var mAlbumSongFragment=AlbumSongFragment()
                        mAlbumSongFragment.arguments=bundle

                            if(artist!=null) {
                                fragmentManager?.beginTransaction()?.apply {
                                    replace(R.id.holder_artist_fragment, mAlbumSongFragment)

                                    addToBackStack(null)
                                    commit()

                                }
                            }

                                else{
                                    fragmentManager?.beginTransaction()?.apply {
                                        replace(R.id.holder_fragment_album,mAlbumSongFragment)
                                        addToBackStack(null)
                                        commit()

                                    }


                        }

                    }


                })
                rv_albums.layoutManager = GridLayoutManager(requireContext(), 2)
                Constants.dismissDialog(dialog)
                rv_albums.adapter = adapter
                ll_albumLoading.visibility=View.GONE
                rv_albums.visibility=View.VISIBLE


            }


        }.execute()


    }

    override fun onDestroy() {
        super.onDestroy()
        task!!.cancel(true)
        task = null
    }


}