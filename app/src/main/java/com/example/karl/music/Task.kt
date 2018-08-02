package com.example.karl.music

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast

class Task(context: Context, progressBar: ProgressBar) : AsyncTask<Int, Int, String>() {
    @SuppressLint("StaticFieldLeak")
    private var progressBar: ProgressBar? = progressBar

    //private var count = 0
    override fun doInBackground(vararg input: Int?): String {
        for (count in 0..input[0]!!) {
            publishProgress(count)
            Thread.sleep(1000)
            if (isCancelled) {
                break
            }
        }


        return "Task completed"
    }

    override fun onPreExecute() {
        progressBar!!.progress = 0
    }

    override fun onProgressUpdate(vararg values: Int?) {
        progressBar!!.progress = values[0]!!
    }

    override fun onCancelled() {
        super.onCancelled()
        progressBar!!.progress = 0
    }

}