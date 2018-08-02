package com.example.karl.music.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    //Audio actributes
    companion object {
        const val TAG:String="MyService"
    }
    override fun onBind(p0: Intent?): IBinder? {
      return null
    }
    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(TAG,"Removed Service.....")
        val nm: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(1234)
        stopSelf()
    }
}