package com.example.learnup.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.learnup.R
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.domain.AppSettingsHolder
import com.example.learnup.domain.GetLearnItemByIdUseCase
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.presentation.fragments.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private val appSettingsHolder  by lazy(LazyThreadSafetyMode.NONE) {
        AppSettingsHolder(LearnRepositoryImpl.getInstance(application))
    }

    @SuppressLint("RestrictedApi")
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

        supportActionBar?.dispatchMenuVisibilityChanged(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentSettings = appSettingsHolder.getAppSettings().value
        val replaceLearnMod = when (currentSettings.wayOfLearn){
            AppSettings.SHOW_WORD -> AppSettings.SHOW_DESCRIPTION
            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_WORD
            else -> AppSettings.SHOW_WORD
        }
        val getNextLearnMod = when (currentSettings.wayOfLearn){
            AppSettings.SHOW_WORD -> AppSettings.SHOW_ALL
            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_ALL
            else -> AppSettings.SHOW_WORD
        }

         when (item.itemId) {
            R.id.setting_replace -> {
                appSettingsHolder.saveAppSettings(AppSettings(replaceLearnMod,currentSettings.wordsFilter))
            }
             R.id.setting_show_all -> {
                 appSettingsHolder.saveAppSettings(AppSettings(currentSettings.wayOfLearn,!currentSettings.wordsFilter))
             }
             R.id.setting_learn_mod -> {
                 appSettingsHolder.saveAppSettings(AppSettings(getNextLearnMod,currentSettings.wordsFilter))
             }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
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