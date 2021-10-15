package com.example.core.domain.usecase

import com.example.core.device.manager.UserManager
import javax.inject.Inject

class ToggleReceiverUseCase @Inject constructor(
    private val userManager: UserManager
) {
    operator fun invoke(receiverKey: String) = userManager.toggleReceiver(receiverKey)
}