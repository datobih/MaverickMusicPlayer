package com.example.maverickmusicplayer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.maverickmusicplayer.fragments.AlbumsFragment
import com.example.maverickmusicplayer.fragments.SongsFragment

class PagerFragmentAdapter(fragmentManager:FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        if(position==0){
            return SongsFragment()
        }

        return AlbumsFragment()

    }
}