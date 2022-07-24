package com.example.learnup.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.learnup.R
import com.example.learnup.data.local.DataBaseHandler
import com.example.learnup.domain.ItemLearn

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
    companion object{
        private fun launchFragment(fragment: Fragment,fragmentManager: FragmentManager) {
            fragmentManager.popBackStack()
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}