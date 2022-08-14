package com.example.learnup.data.repositories

import android.app.Application
import android.util.Log
import com.example.learnup.domain.models.AppSettings
import com.example.learnup.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class SettingsRepositoryImpl @Inject constructor(val application: Application):SettingsRepository {
    private val MODE_PRIVATE = 0;
    private val sharedPrefsName = "changetItemIds"
    private val SHARED_PREF_SETTINGS = "settings"
    private val WAY_LEARN = "wayOfLearn"
    private val FILTERED = "filtered"
    private val RANDOM = "random"
    var settingsStateFlow:MutableStateFlow<AppSettings> = MutableStateFlow(getCurrentSettings())

    override fun saveAppSettings(settings: AppSettings) {
        val shar = application.getSharedPreferences(SHARED_PREF_SETTINGS,MODE_PRIVATE)
        shar.edit().putInt(WAY_LEARN, settings.wayOfLearn).commit()
        shar.edit().putBoolean(FILTERED, settings.wordsFilter).commit()
        shar.edit().putBoolean(RANDOM, settings.random).commit()
        Log.d("test1", "saveAppSettings")
        settingsStateFlow.value = settings
    }

    private fun getCurrentSettings(): AppSettings {

        Log.d("test1", "get new APP SETTINGS")
        val shar = application.getSharedPreferences(SHARED_PREF_SETTINGS,MODE_PRIVATE)
        val wayOfLearn = shar.getInt(WAY_LEARN, AppSettings.SHOW_ALL)
        val wordsFilter = shar.getBoolean(FILTERED,true)
        val random = shar.getBoolean(RANDOM,false)
        return AppSettings(wayOfLearn = wayOfLearn, wordsFilter = wordsFilter, random = random)
    }

    override fun getAppSettings(): MutableStateFlow<AppSettings> {
        settingsStateFlow.value = getCurrentSettings()
        return settingsStateFlow
    }

    companion object {

        private var mInstance: SettingsRepositoryImpl? = null

        @JvmStatic
        fun getInstance(application: Application): SettingsRepositoryImpl {
            if (mInstance == null) {
                mInstance = SettingsRepositoryImpl(application)
            }
            return mInstance!!
        }
    }
}