package com.example.karl.music.view

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karl.music.R
import com.example.karl.music.Task
import com.example.karl.music.util.SongUtil
import com.example.karl.music.model.Song
import com.example.karl.music.service.SongPlayer
import com.example.karl.music.service.impl.SongPlayerimpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.fragment_library.*
import java.util.concurrent.TimeUnit

class FragmentLibrary : Fragment(), SongAdapter.OnItemClickListener, View.OnClickListener {
    var song: Song? = null
    private var task: Task? = null
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ibNext -> {
                nextSong()
            }
            else -> {

            }
        }
    }

    private fun nextSong() {
        stopTask()
        indexSong++
        if (indexSong > adapter!!.itemCount) {
            indexSong = 0
        }
        if (songpPlayer!!.isSongPlaying()) {
            songpPlayer!!.stop()
        }
        val song = adapter!!.getItem(indexSong)
        songpPlayer!!.play(song.uri!!)
        notficationDisplay(song)
        val tvShow = activity!!.tvShow
        tvShow.setSingleLine()
        tvShow.isSelected = true
        tvShow.text = song.title + "-" + song.singer
        this.song = song
        val secondsDuration = song.duration!! / 1000
        activity!!.pgbSong.max = secondsDuration
        task = Task(context!!, activity!!.pgbSong)
        task!!.execute(secondsDuration)
    }

    private var indexSong = -1
    private var manager: NotificationManager? = null
    private var remoteView: RemoteViews? = null
    var mediaPlayer: MediaPlayer? = null
    private var songpPlayer: SongPlayer? = null
    private var builder: NotificationCompat.Builder? = null

    companion object {
        const val TAG: String = "FragmentLibrary"
        const val CHANEL_ID: String = "Music"
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClicked(position: Int, song: Song) {
        stopTask()
        this.song = song
        //stopPlaying(song)
        indexSong = position
//        val uri = Uri.parse(song.uri)
//        stopPlaying()
//        mediaPlayer = MediaPlayer.create(context, uri)
//        mediaPlayer!!.start()
        val tvShow = activity!!.tvShow
        tvShow.setSingleLine()
        tvShow.isSelected = true
        tvShow.text = song.title + "-" + song.singer
        songpPlayer!!.play(song.uri!!)
        notficationDisplay(song)
        val secondsDuration = song.duration!! / 1000
        activity!!.pgbSong.max = secondsDuration
        task = Task(context!!, activity!!.pgbSong)
        task!!.execute(secondsDuration)
        if (songpPlayer!!.isSongPlaying()) {
            songpPlayer!!.stop()
        }
        val time = String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(song.duration!!.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(song.duration!!.toLong()),
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(song.duration!!.toLong())))
    }

    private fun stopTask() {
        if (task!=null){
            task!!.cancel(true)
            task=null
        }

    }

    private fun notficationDisplay(song: Song) {
        val bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher)
        manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        remoteView = RemoteViews(activity!!.packageName, R.layout.activity_notification)
        remoteView!!.setTextViewText(R.id.tvNameNoti, song.title)
        remoteView!!.setTextViewText(R.id.tvArtistNoti, song.singer)
        builder = NotificationCompat.Builder(context!!, CHANEL_ID)
                .setCustomBigContentView(remoteView)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(bitmap)
                .setOngoing(true)
        manager!!.notify(1234, builder!!.build())
    }

    private fun stopPlaying(song: Song) {
        if (songpPlayer != null) {
            songpPlayer!!.stop()
            songpPlayer!!.play(song.uri!!)
            //mediaPlayer!!.reset()
            songpPlayer = null
        }
    }


    private var songs1: ArrayList<Song>? = null
    private var adapter: SongAdapter? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (songpPlayer == null) {
            songpPlayer = SongPlayerimpl()
        }
        activity!!.ibNext.setOnClickListener(this)
        initData()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initData() {
        songs1 = ArrayList()
        rcvMusic.layoutManager = LinearLayoutManager(context)
        rcvMusic.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        val height: Int = arguments!!.getInt("valueHeight")
        rcvMusic.setPadding(0, 0, 0, height+activity!!.cvSongBAr.height)
        val songManager = SongUtil()
        val songs = songManager.getSongs(context!!)
        for (song: Song in songs) {
            songs1!!.add(song)
        }
        adapter = SongAdapter(context!!, songs1!!)
        rcvMusic.adapter = adapter
        adapter!!.setOnItemClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "On destroy.....")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "On DestroyView.....")

    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "On Detach.....")
    }

}