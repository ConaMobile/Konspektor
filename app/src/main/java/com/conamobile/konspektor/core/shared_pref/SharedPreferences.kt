package com.conamobile.konspektor.core.shared_pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val pref = context.getSharedPreferences("key", Context.MODE_PRIVATE)

    fun isSavedTextInstance(isSavedTextInstance: String) {
        val editor = pref.edit()
        editor.putString("isSavedTextInstance", isSavedTextInstance)
        editor.apply()
    }

    fun getSavedTextInstance(): String? {
        return pref.getString("isSavedTextInstance", "")
    }
}