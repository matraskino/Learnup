package com.example.learnup.presentation

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learnup.R
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.presentation.fragments.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }


    //will delete synhronisation laiter, when implement it on service or otherwise
    override fun onPause() {
        val repository = LearnRepositoryImpl.getInstance(this.application)
        runBlocking {
            withContext(Dispatchers.IO){
                repository.synhronise()
            }
        }
        super.onPause()
    }
}