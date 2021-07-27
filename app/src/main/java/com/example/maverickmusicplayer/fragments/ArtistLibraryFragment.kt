package com.example.maverickmusicplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.adapters.PagerArtistFragmentAdapter
import com.example.maverickmusicplayer.constants.Constants
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_artist_library.*


class ArtistLibraryFragment : Fragment() {

var artist:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val bundle=this.arguments
        artist=bundle?.getString(Constants.ARTIST_NAME_BUNDLE)

        return inflater.inflate(R.layout.fragment_artist_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab_artist.addTab(tab_artist.newTab().setText("Albums"))
        tab_artist.addTab(tab_artist.newTab().setText("Tracks"))


        var adapter=PagerArtistFragmentAdapter(childFragmentManager,lifecycle,artist!!)
        vp_artist.adapter=adapter


        tab_artist.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_artist.currentItem=tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })



        vp_artist.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            tab_artist.selectTab(tab_artist.getTabAt(position))

            }

        })

    }


}