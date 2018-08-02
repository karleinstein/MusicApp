package com.example.karl.music.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.karl.music.R
import com.example.karl.music.model.Song
import kotlinx.android.synthetic.main.item_gridview.view.*

class GridAdapter(val context:Context, private var grids:ArrayList<Song>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val view:View?
        val viewHolder: ViewHolder
        if (p1==null){
            val inflater=LayoutInflater.from(context)
            view=inflater.inflate(R.layout.item_gridview,p2,false)
            viewHolder= ViewHolder(view)
            view?.tag=viewHolder
        }
        else{
            view=p1
            viewHolder=view.tag as ViewHolder
        }

        val song=grids[position]
        viewHolder.tvName!!.text=song.title
        viewHolder.tvArtist!!.text=song.singer
        viewHolder.imvSong!!.setImageURI(song.uriImage)
        return view as View
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return grids.size
    }
    private class ViewHolder(view:View?){
        var tvName:TextView?=null
        var tvArtist:TextView?=null
        var imvSong:ImageView?=null
        init {
            this.tvName=view!!.tvNameSong
            this.tvArtist= view.tvnameArtist
            this.imvSong= view.imvSong
        }
    }
}