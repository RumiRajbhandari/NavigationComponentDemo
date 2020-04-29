package com.rumi.navigationcomponentdemo.data

import android.content.Context
import androidx.core.content.edit

class SharedPreferenceManager constructor(var context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)

    var nightModeStatus: Boolean
        get() = sharedPreferences.getBoolean(Constants.PREF_NIGHT_MODE, false)
        set(value) = sharedPreferences.edit { putBoolean(Constants.PREF_NIGHT_MODE, value) }

    fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }
}