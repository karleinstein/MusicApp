package com.example.karl.music.service

import android.widget.SeekBar

interface SongPlayer {
    fun play(uri: String)
    fun pause()
    fun resume()
    fun stop()
    fun isSongPlaying(): Boolean
    fun seek(duration: Int)
    fun getCurrentPosition(): Int

}