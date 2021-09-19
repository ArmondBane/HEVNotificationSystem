package com.example.hevnotificationsystem.receiver

import android.content.Context
import com.example.core.data.local.APP_SHARED_PREFERENCES_FILE
import com.example.core.data.local.RECEIVERS_KEY
import com.google.gson.Gson
import java.lang.Exception

abstract class HEVReceiver {

    abstract fun getReceiverKey(): String

    abstract fun getAppContext(): Context

    init {
        saveReceiver()
    }

    private fun saveReceiver() {
        val sharedPreferences =
            getAppContext().getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

        val data = sharedPreferences.getString("p_$RECEIVERS_KEY", "")!!
        val saveReceivers = try {
            Gson().fromJson(data, Array<String>::class.java).toMutableList()
        } catch (ex: Exception) {
            null
        }

        val newSaveReceivers = saveReceivers?.apply {
            this.add(getReceiverKey())
        }?.toList() ?: listOf(getReceiverKey())

        try {
            with(sharedPreferences.edit()) {
                putString("p_$RECEIVERS_KEY", Gson().toJson(saveReceivers))
                apply()
            }
        } catch (ex: Exception) {}
    }
}