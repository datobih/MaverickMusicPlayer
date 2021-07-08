package com.example.maverickmusicplayer.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.example.maverickmusicplayer.fragments.AlbumsFragment
import com.example.maverickmusicplayer.fragments.SongsFragment

class PagerArtistFragmentAdapter(val fragmentManager: FragmentManager,val lifecycle: Lifecycle,val artist:String):FragmentStateAdapter(fragmentManager,lifecycle)  {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
      if(position==0){
          var bundle=Bundle()
          bundle.putString(Constants.ARTIST_ALBUM_BUNDLE,artist)
          val albumFragment=AlbumsFragment()
          albumFragment.arguments=bundle
          return albumFragment



      }

        val tracksFragment=SongsFragment()
        val bundle=Bundle()
        bundle.putString(Constants.ARTIST_TRACKS_BUNDLE,artist)
        tracksFragment.arguments=bundle
        return tracksFragment


    }


}