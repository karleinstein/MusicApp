package com.example.karl.music.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karl.music.R
import com.example.karl.music.model.Song
import kotlinx.android.synthetic.main.items_song.view.*

class SongAdapter(context: Context, var songs: ArrayList<Song>) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private val inflater = LayoutInflater.from(context)
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.items_song, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs[position]
        holder.tvName.text = song.title
        holder.tvSinger.text = song.singer
        holder.itemView.setOnClickListener {
            onItemClickListener!!.onItemClicked(position, song)
            selectedPosition = position
            notifyDataSetChanged()
        }
        if (selectedPosition == position) {
            holder.tvSinger.setTextColor(Color.parseColor("#019f57"))
            holder.tvName.setTextColor(Color.parseColor("#019f57"))
        } else {
            holder.tvSinger.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvName.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvName!!
        var tvSinger = itemView.tvArtist!!
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun getItem(position: Int): Song {
        return songs[position]
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, song: Song)
    }

}