package com.example.karl.music.service.impl

import android.media.MediaPlayer
import com.example.karl.music.service.SongPlayer

class SongPlayerimpl : SongPlayer {
    override fun seek(duration: Int) {
        if (songState != SongState.IDLE) {
            mediaPlayer!!.seekTo(duration)
        }
    }

    override fun isSongPlaying(): Boolean {
        return songState == SongState.PLAYING
    }

    private var mediaPlayer: MediaPlayer? = null

    private enum class SongState {
        IDLE,  //0
        PLAYING,  //1
        PAUSED  //2
    }

    private var songState: SongState? = null
    override fun play(uri: String) {
        //val x="PAUSED"
        //SongState.valueOf(x)
        if (songState != SongState.IDLE) {
            return
        }
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setDataSource(uri)
        mediaPlayer!!.prepare()

        //Internet
        //mediaPlayer!!.prepareAsync()

        //prepare doi cho du lieu available
        mediaPlayer!!.setOnPreparedListener {
            mediaPlayer!!.start()
            songState = SongState.PLAYING
        }
        mediaPlayer!!.setOnCompletionListener {
            mediaPlayer!!.release()
            mediaPlayer = null
            songState = SongState.IDLE
        }
    }

    override fun pause() {
        if (songState == SongState.PLAYING) {
            mediaPlayer!!.pause()
            SongState.PAUSED
        }
    }

    override fun resume() {
        if (songState == SongState.PAUSED) {
            mediaPlayer!!.start()
            songState = SongState.PLAYING
        }
    }

    override fun stop() {
        if (songState != SongState.IDLE) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
            songState = SongState.IDLE
        }
    }

    //init dung trong empty contructor
    init {
        songState = SongState.IDLE
    }
}