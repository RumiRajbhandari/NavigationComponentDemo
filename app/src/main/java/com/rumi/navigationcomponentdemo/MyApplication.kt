package com.rumi.navigationcomponentdemo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.rumi.navigationcomponentdemo.data.SharedPreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    private val sharedPreferenceManager by lazy { SharedPreferenceManager(this) }

    override fun onCreate() {
        super.onCreate()
        initDarkorLightMode()
    }

    private fun initDarkorLightMode() {
        if (sharedPreferenceManager.nightModeStatus) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}