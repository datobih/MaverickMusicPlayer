package com.example.maverickmusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.maverickmusicplayer.R
import com.example.maverickmusicplayer.activities.MainActivity
import com.example.maverickmusicplayer.interfaces.SongOnClickListener
import com.example.maverickmusicplayer.models.Music
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_music.view.*

class SongsRecyclerAdapter(val context: Context,val musicList:ArrayList<Music>,val parent: Boolean):RecyclerView.Adapter<SongsRecyclerAdapter.ViewHolder>() ,Filterable{


val completeMusicList=ArrayList<Music>(musicList)
var connected=false
var songOnClickListener:SongOnClickListener?=null
    var pos:Int?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_music,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var title=musicList[position].name
       var sb=StringBuilder()
        sb.append(title)

        sb.replace((sb.toString().lastIndex)-3,sb.toString().length,"")

        title=sb.toString()



            holder.songCover.setImageBitmap(musicList[position].albumArt)



        holder.songTitle.text=title
        holder.songArtist.text=musicList[position].artist




        holder.songLayout.setOnClickListener {


              if(context is MainActivity){
//before here
                 context.setPlayingAdapter(musicList)


                    context.ll_songPlaying.visibility=View.VISIBLE




               songOnClickListener!!.onItemClicked(position)


/*
                  if(context.playbackThread!=null){
                      context.playbackThread?.stop()
                      context.playbackThread?.destroy()
                      context.playbackThread=null
                  }
                  context.playbackThread =Thread(PlaybackThread(context,musicList,position))
                  context.playbackThread?.start()


 */

                  /*
                    if(context.mediaPlayer!!.isPlaying){
                        context.mediaPlayer?.stop()
                        context.mediaPlayer?.release()
                        context.mediaPlayer=null
                    }
                    context.mediaPlayer= MediaPlayer()
                    context.mediaPlayer?.setDataSource(context,musicList[position].uri!!)
                    context.mediaPlayer?.prepare()
                    context.mediaPlayer?.start()



                   */

            }




        }




    }

    override fun getItemCount(): Int {
      return musicList.size
    }

    fun setOnSongClicked(mSongOnClickListener: SongOnClickListener){
        this.songOnClickListener=mSongOnClickListener
    }


    val exampleFilter=object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
var filteredList=ArrayList<Music>()
            val searchPattern=constraint.toString().toLowerCase().trim()
            if(searchPattern.isNullOrEmpty()){
filteredList.addAll(completeMusicList)
            }
else {
                for (i in completeMusicList) {

                    if (i.name!!.toLowerCase().contains(searchPattern)) {
                         pos=completeMusicList.indexOf(i)
                        filteredList.add(i)
                    }
                }
            }

            val filterResults=FilterResults()
            filterResults.values=filteredList

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

           musicList.clear()

           musicList.addAll(results?.values as ArrayList<Music>)

            notifyDataSetChanged()

        }

    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var songTitle=view.tv_songTitle
        var songCover: ImageView =view.imv_songCover
        var songArtist=view.tv_songArtist
        var songLayout=view.ll_songItem


    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}