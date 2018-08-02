package com.example.karl.music.model

import android.net.Uri
import java.net.URI

class Song(title: String, singer: String, duration: Int, uri: String, uriImage: Uri) {
    var title: String? = title
    var singer: String? = singer
    var duration: Int? = duration
    var uri: String? = uri
    var uriImage: Uri? = uriImage

    override fun toString(): String {
        return "Song(title=$title, singer=$singer, duration=$duration, uri=$uri)"
    }


}