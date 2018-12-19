package com.example.karl.music

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.time.Duration

@SuppressLint("StaticFieldLeak")
class Task(context: Context,
           private var progressBar: ProgressBar?,
           private var seekBar: SeekBar?,
           private var textView: TextView?, private var duration: Int) : AsyncTask<Int, Int, String>() {
    private var min = 0
    private var sec = 0


    //private var count = 0
    override fun doInBackground(vararg input: Int?): String {
        var count = 0
        while (count < input[0]!!) {
            count++
            publishProgress(count)
            Thread.sleep(1000)
            if (isCancelled) {
                break
            }
        }

        return "Task completed"
    }

    override fun onPreExecute() {
        progressBar!!.max = duration
        progressBar!!.progress = 0
        seekBar!!.max = duration
        seekBar!!.progress = 0
    }

    @SuppressLint("SetTextI18n")
    override fun onProgressUpdate(vararg values: Int?) {
        sec++
        progressBar!!.progress = values[0]!!
        seekBar!!.progress = values[0]!!

        if (sec % 60 == 0) {
            sec = 0
            min++
        }
        if (sec < 10) {
            textView!!.text = "$min:0$sec"
        } else {
            textView!!.text = "$min:$sec"
        }


    }


}