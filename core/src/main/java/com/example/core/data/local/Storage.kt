package com.example.core.data.local

import android.os.Parcelable


interface Storage {

    fun getBoolean(key: String): Boolean

    fun setBoolean(key: String, value: Boolean)

    fun <T> getParcelable(key: String, classT: Class<T>): T?

    fun setParcelable(key: String, value: Parcelable)
}