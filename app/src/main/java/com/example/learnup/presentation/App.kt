package com.example.learnup.presentation

import android.app.Application
import com.example.learnup.di.AppModule
import com.example.learnup.di.ApplicationComponent
import com.example.learnup.di.DaggerApplicationComponent
import com.example.learnup.di.DataModule

class App : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {


        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .build()
    }
}