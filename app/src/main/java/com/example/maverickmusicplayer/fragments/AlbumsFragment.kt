package com.example.maverickmusicplayer.fragments

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.DeviceMediaHandler
import com.example.maverickmusicplayer.adapters.AlbumRecyclerAdapter
import com.example.maverickmusicplayer.models.Album
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.fragment_albums.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlbumsFragment : Fragment() {

    var task:AsyncTask<Void,Void,ArrayList<Album>>?=null
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task=object : AsyncTask<Void, Void, ArrayList<Album>>(){
            override fun doInBackground(vararg params: Void?): ArrayList<Album> {
                var albums = DeviceMediaHandler(requireContext()).getAlbums()

                return albums
            }

            override fun onPostExecute(result: ArrayList<Album>?) {
                super.onPostExecute(result)
                var adapter=AlbumRecyclerAdapter(requireContext(),result!!)
                rv_albums.layoutManager=GridLayoutManager(requireContext(),2)
                rv_albums.adapter=adapter


            }


        }.execute()






    }

    override fun onDestroy() {
        super.onDestroy()
        task!!.cancel(true)
        task=null
    }


}