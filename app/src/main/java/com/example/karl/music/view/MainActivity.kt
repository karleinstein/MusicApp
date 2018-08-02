package com.example.karl.music.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.karl.music.service.MyService
import com.example.karl.music.R
import com.example.karl.music.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var height = 0
    private var countBack = 0
    private var fragmentLibrary: FragmentLibrary? = null
    private var fragmentHome: FragmentHome? = null
    //private var pgbSong: ProgressBar? = null

    companion object {
        const val LIBRARY_FRAGMENT: String = "library_fragment"
        const val HOME_FRAGMENT: String = "home_fragment"
        const val TAG: String = "MainActivity"
    }

    private fun nextSong() {

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.itemSearch -> {
                supportActionBar!!.title = "Search"
            }
            R.id.itemLibrary -> {
                supportActionBar!!.title = "Library"
                if (fragmentLibrary == null) {
                    fragmentLibrary = FragmentLibrary()
                }
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragmentLibrary!!, LIBRARY_FRAGMENT)
                        .addToBackStack(null)
                        .commit()

                fragmentLibrary!!.arguments = addHeightBtmNavigation()
                startService(Intent(baseContext, MyService::class.java))

            }
            R.id.itemHome -> {
                supportActionBar!!.title = "Home"
                if (fragmentHome == null) {
                    fragmentHome = FragmentHome()

                }
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragmentHome!!)
                        .addToBackStack(null)
                        .commit()

                fragmentHome!!.arguments = addHeightBtmNavigation()
            }
        }
        return true
    }

    private fun addHeightBtmNavigation(): Bundle {
        val bundle = Bundle()
        bundle.putInt("valueHeight", height)
        return bundle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //pgbSong = findViewById(R.id.pgbSong)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        btmNavigation.setOnNavigationItemSelectedListener(this)
        supportActionBar!!.title = "Home"
        if (fragmentHome == null) {
            fragmentHome = FragmentHome()
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragmentHome!!)
                .addToBackStack(null)
                .commit()

        fragmentHome!!.arguments = addHeightBtmNavigation()
        btmNavigation.post {
            kotlin.run {
                height = btmNavigation.measuredHeight
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            return
        } else {

            countBack++
            if (countBack == 1) {
                Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT).show()
            }
            if (countBack == 2) {
                finishAndRemoveTask()
            }
        }


    }

}
