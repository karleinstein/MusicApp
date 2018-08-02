package com.example.karl.music

import android.app.Activity
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log

class App : Application() {
//    companion object {
//        var instance:App?=null
//        fun getInstancez():App{
//            if (instance==null){
//                instance= App()
//            }
//            return instance as App
//        }
//    }
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity?) {

            }

            override fun onActivityResumed(p0: Activity?) {

            }

            override fun onActivityStarted(p0: Activity?) {

            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

            }

            override fun onActivityStopped(p0: Activity?) {

            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {

            }

            override fun onActivityDestroyed(p0: Activity?) {
                val nm: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.cancelAll()
            }

        }
        )
    }
}