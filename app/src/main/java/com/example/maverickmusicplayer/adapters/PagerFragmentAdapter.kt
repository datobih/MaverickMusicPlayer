package com.example.maverickmusicplayer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.maverickmusicplayer.fragments.HolderAlbumsFragment
import com.example.maverickmusicplayer.fragments.HolderArtistsFragment
import com.example.maverickmusicplayer.fragments.SongsFragment

class PagerFragmentAdapter(fragmentManager:FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    val songsFragment=SongsFragment()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        if(position==0){
            return songsFragment
        }
        if(position==1){
            return HolderAlbumsFragment()
        }

        return HolderArtistsFragment()

    }
}