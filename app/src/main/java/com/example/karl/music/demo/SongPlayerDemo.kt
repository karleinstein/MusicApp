package com.example.karl.music.demo

import android.content.Context
import android.media.MediaPlayer
import com.example.karl.music.R

class SongPlayerDemo {
    //giúp thực thi các cái file âm thanh hoặc video audio
    //Texture View
    //Video view
    //Nguồn dữ liệu : 1.Stream(Internet), 2.Resource, 3.Storage
    //Exo Player
    var mediaPLayer:MediaPlayer?=null
    fun playSongfromResource(context:Context){
        mediaPLayer=MediaPlayer.create(context, R.raw.song)
        mediaPLayer!!.start()
    }
    fun stop(){
        mediaPLayer!!.stop()
    }




}