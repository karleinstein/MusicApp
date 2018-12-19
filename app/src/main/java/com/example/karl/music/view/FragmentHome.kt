package com.example.karl.music.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.karl.music.R
import com.example.karl.music.util.SongUtil
import com.example.karl.music.model.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val height: Int = arguments!!.getInt("valueHeight")
        layout.setPadding(0, 0, 0, height + activity!!.cvSongBAr.height)
        val grids = ArrayList<Song>()
        val songManager = SongUtil()
        val songs = songManager.getSongs(context!!)
        for (song: Song in songs) {
            grids.add(song)
        }
        val gridAdapter = GridAdapter(activity!!, grids)
        gvHome.adapter = gridAdapter

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}