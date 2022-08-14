package com.example.learnup.di

import com.example.learnup.presentation.MainActivity
import com.example.learnup.presentation.fragments.MainFragment
import com.example.learnup.presentation.viewModels.EditLearnItemViewModel
import com.example.learnup.presentation.viewModels.LearnItemViewModel
import com.example.learnup.presentation.viewModels.MainViewModel
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)

    fun inject(editLearnItemViewModel: EditLearnItemViewModel)

    fun inject(learnItemViewModel: LearnItemViewModel)

    fun inject(mainViewModel: MainViewModel)
}