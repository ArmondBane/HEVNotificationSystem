package com.example.core.device.manager

import com.example.core.data.local.RECEIVERS_KEY
import com.example.core.data.local.Storage
import com.example.core.device.receiver.HEVReceiver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val storage: Storage
) {

    val receivers = storage.getParcelable(RECEIVERS_KEY, Array<String>::class.java)

    fun toggleReceiver(receiverKey: String) {
        val oldValue = storage.getBoolean(receiverKey)

        storage.setBoolean(
            receiverKey,
            !oldValue
        )


    }
}