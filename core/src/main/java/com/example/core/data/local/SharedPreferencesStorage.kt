package com.example.core.data.local

import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

const val APP_SHARED_PREFERENCES_FILE = "H.E.V._storage"
const val RECEIVERS_KEY = "Receivers"

@Singleton
class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context
) : Storage {
    private val sharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)

    override fun getBoolean(key: String) =
        sharedPreferences.getBoolean(key, false)

    override fun setBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    override fun <T> getParcelable(key: String, classT: Class<T>): T? {
        val data = sharedPreferences.getString("p_$key", "")!!
        return try {
            Gson().fromJson(data, classT)
        } catch (ex: Exception) {
            null
        }
    }

    override fun setParcelable(key: String, value: Parcelable) {
        try {
            with(sharedPreferences.edit()) {
                putString("p_$key", Gson().toJson(value))
                apply()
            }
        } catch (ex: Exception) {}
    }
}