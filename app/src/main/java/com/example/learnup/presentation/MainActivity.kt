package com.example.learnup.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.example.learnup.R
import com.example.learnup.data.LearnRepositoryImpl
import com.example.learnup.domain.AppSettingsHolder
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
        val menuLayout = R.menu.menu_main
        val currentSettings = appSettingsHolder.getAppSettings().value

            menuInflater.inflate(R.menu.menu_main, menu)

        menu.forEach {
            when (it.itemId) {
                R.id.setting_checked_only -> {
                    it.isChecked = currentSettings.wordsFilter
                }

                R.id.setting_random -> {
                    it.isChecked = currentSettings.random
                }
                R.id.setting_learn_mod -> {
                    it.isChecked = currentSettings.wayOfLearn != AppSettings.SHOW_ALL
                }
                }

            }


        return true
    }

    fun fillCurrentSettingsToMenu(){

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //todo Refactor function. Not to request current settings and save only one settings(not rewrite all)
        val currentSettings = appSettingsHolder.getAppSettings().value
        val replaceLearnMod = when (currentSettings.wayOfLearn){
            AppSettings.SHOW_WORD -> AppSettings.SHOW_DESCRIPTION
            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_WORD
            else -> AppSettings.SHOW_ALL
        }
        val getNextLearnMod = when (currentSettings.wayOfLearn){
            AppSettings.SHOW_WORD -> AppSettings.SHOW_ALL
            AppSettings.SHOW_DESCRIPTION -> AppSettings.SHOW_ALL
            else -> AppSettings.SHOW_WORD
        }

         when (item.itemId) {
            R.id.setting_replace -> {
                appSettingsHolder.saveAppSettings(AppSettings(replaceLearnMod,currentSettings.wordsFilter,currentSettings.random))
            }
             R.id.setting_checked_only -> {
                 appSettingsHolder.saveAppSettings(AppSettings(currentSettings.wayOfLearn,!currentSettings.wordsFilter,currentSettings.random))
                 item.isChecked =!item.isChecked
             }
             R.id.setting_learn_mod -> {
                 appSettingsHolder.saveAppSettings(AppSettings(getNextLearnMod,currentSettings.wordsFilter,currentSettings.random))
                 item.isChecked =!item.isChecked
             }
             R.id.setting_random -> {
                 val newSetting = !currentSettings.random
                 appSettingsHolder.saveAppSettings(AppSettings(currentSettings.wayOfLearn,currentSettings.wordsFilter,!currentSettings.random))
                 item.isChecked =!item.isChecked
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