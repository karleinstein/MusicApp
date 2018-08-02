package com.example.karl.music.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.karl.music.model.Song
import java.sql.Time
import java.util.concurrent.TimeUnit

class SongUtil {
    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getSongs(context: Context): List<Song> {
        val songs = ArrayList<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projections = arrayOf(MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID)

        //dùng để query các dữ liệu của db của provider
        //contenresolver để lấy các dữ liệu từ provider về
        //Uri là địa chỉ để trỏ đến db
        //Internal là báo thức
        //projection là những cột trong external nếu là null thì là lấy hết

        val cursor = context.contentResolver
                .query(uri, projections, null, null
                        , MediaStore.Audio.Media.TITLE + " ASC") ?: return songs
        if (cursor.count == 0) {
            cursor.close()
            return songs
        }
        val indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val indexSinger = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val data = cursor.getString(indexData)
            val title = cursor.getString(indexTitle)
            val singer = cursor.getString(indexSinger)
            val duration = cursor.getInt(indexDuration)
            val albumId = cursor.getLong(indexAlbumId)
            Log.d("test", albumId.toString())
            val artworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumIdUri = ContentUris.withAppendedId(artworkUri, albumId)
            val song = Song(title, singer, duration, data, albumIdUri)
            songs.add(song)
            cursor.moveToNext()
            //cursor.close()
        }
        return songs


    }

}