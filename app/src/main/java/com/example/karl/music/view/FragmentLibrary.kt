package com.example.karl.music.view

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.karl.music.R
import com.example.karl.music.Task
import com.example.karl.music.util.SongUtil
import com.example.karl.music.model.Song
import com.example.karl.music.service.SongPlayer
import com.example.karl.music.service.impl.SongPlayerimpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.android.synthetic.main.item_gridview.*
import kotlinx.android.synthetic.main.items_song.*
import java.io.Serializable
import java.util.concurrent.TimeUnit

class FragmentLibrary : Fragment(), SongAdapter.OnItemClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    var song: Song? = null
    private val songs2 = ArrayList<Song>()
    private var position = 0
    private var progress: Int? = null
    private var isSongClicked = false
    private var indexSong = -1
    private var manager: NotificationManager? = null
    private var remoteView: RemoteViews? = null
    var mediaPlayer: MediaPlayer? = null
    private var songpPlayer: SongPlayer? = null
    private var builder: NotificationCompat.Builder? = null
    private var task: Task? = null
    private var seconds = 0
    private var secondsDuration = 0

    companion object {
        const val TAG: String = "FragmentLibrary"
        const val CHANEL_ID: String = "Music"
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p2) {
            progress = p1
            songpPlayer!!.seek(p1 * 1000)
//            if (task!!.status == AsyncTask.Status.RUNNING) {
//                task!!.cancel(true)
//            }
            isSongClicked = true
            mediaLifeCycle()
        }

    }

    private fun mediaLifeCycle() {
        if (isSongClicked) {
            val mHandler = Handler()
            activity!!.runOnUiThread(object : Runnable {
                override fun run() {
                    if (songpPlayer != null) {
                        val mCurrentPosition = songpPlayer!!.getCurrentPosition() / 1000
                        if (activity != null && activity!!.sbDuration != null && activity!!.tvTimeDuration != null) {
                            activity!!.sbDuration.progress = mCurrentPosition
                            activity!!.pgbSong.progress = mCurrentPosition
                            val min = mCurrentPosition / 60
                            val sec = mCurrentPosition % 60
                            activity!!.tvTimeDuration.text = "$min:$sec"
                        }

                    }
                    mHandler.postDelayed(this, 1000)
                }
            })
            isSongClicked = false

        }

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    // private var onSendPositiontoDetailF: OnSendPositiontoDetailF? = null

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ibNext -> {
                nextSong()
            }
            R.id.ibUp -> {
                activity!!.btmNavigation.visibility = View.GONE
                activity!!.btnDetail.visibility = View.GONE
                (activity as AppCompatActivity).supportActionBar!!.hide()
                if (layoutDetail != null) {
                    layoutDetail.visibility = View.VISIBLE
                }
                if (rcvMusic!=null){
                    rcvMusic.visibility = View.GONE
                }


            }
            R.id.ibDown -> {
                layoutDetail.visibility = View.GONE
                rcvMusic.visibility = View.VISIBLE
                activity!!.btmNavigation.visibility = View.VISIBLE
                activity!!.btnDetail.visibility = View.VISIBLE
                (activity as AppCompatActivity).supportActionBar!!.show()
            }
            else -> {

            }
        }
    }

    private fun nextSong() {
        //stopTask()
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
        activity!!.sbDuration.max = secondsDuration
//        if (activity!!.sbDuration != null) {
//            task = Task(context!!, activity!!.pgbSong, activity!!.sbDuration, activity!!.tvTimeDuration,
//                    secondsDuration)
//        }
//        task!!.execute(secondsDuration)
        isSongClicked = true
        mediaLifeCycle()
    }

    @SuppressLint("SetTextI18n")
    override fun onItemClicked(position: Int, song: Song) {
        this.song = song
        if (songpPlayer!!.isSongPlaying()) {
            songpPlayer!!.stop()
        }
        //stopPlaying(song)
        indexSong = position
        activity!!.sbDuration.setOnSeekBarChangeListener(this)
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
        secondsDuration = song.duration!! / 1000
        seconds = secondsDuration
        val min: Int = secondsDuration / 60
        val sec: Int = secondsDuration % 60
        //activity!!.sbDuration?.max = secondsDuration
        activity!!.ivSong.setImageURI(song.uriImage)
        activity!!.tvNameDetail.text = song.title
        activity!!.tvArtistDetail.text = song.singer
        activity!!.tvTimeFinished.text = "$min:$sec"
//        task = Task(context!!, activity!!.pgbSong, activity!!.sbDuration, activity!!.tvTimeDuration,
//                secondsDuration)
//        task!!.execute(secondsDuration)
        activity!!.pgbSong.max = secondsDuration
        activity!!.sbDuration.max = secondsDuration
        isSongClicked = true
        mediaLifeCycle()
        //onSendPositiontoDetailF!!.onSentPosition(song)


    }

//    private fun stopTask() {
//        if (task != null) {
//            task!!.cancel(true)
//            task = null
//        }
//
//    }

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
        activity!!.ibUp.setOnClickListener(this)
        initData()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initData() {
        ibDown.setOnClickListener(this)
        songs1 = ArrayList()
        rcvMusic.layoutManager = LinearLayoutManager(context)
        rcvMusic.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        val height: Int = arguments!!.getInt("valueHeight")
        rcvMusic.setPadding(0, 0, 0, height + activity!!.cvSongBAr.height)
        val songManager = SongUtil()
        val songs = songManager.getSongs(context!!)
        for (song: Song in songs) {
            songs1!!.add(song)
        }
        adapter = SongAdapter(context!!, songs1!!)
//        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_rcv_library)!!)
//        rcvMusic.addItemDecoration(dividerItemDecoration)

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