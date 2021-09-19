package com.example.core.util

import com.example.core.data.local.RECEIVERS_KEY
import com.example.core.data.local.Storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val storage: Storage
) {

    val receivers = storage.getParcelable(RECEIVERS_KEY, Array<String>::class.java)

    fun toggleReceiver(receiver: HEVReceiver) {
        storage.setBoolean(
            receiver.name,
            !storage.getBoolean(receiver.name)
        )
    }

    class HEVReceiver(val name: String)
}