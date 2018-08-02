package com.example.karl.music.service

interface SongPlayer {
    fun play(uri:String)
    fun pause()
    fun resume()
    fun stop()
    fun isSongPlaying():Boolean
    fun seek(duration:Int)

}