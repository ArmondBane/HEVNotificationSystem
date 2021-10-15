package com.example.core.domain.usecase

import com.example.core.device.manager.UserManager
import javax.inject.Inject

class GetReceiversUseCase @Inject constructor(
    private val userManager: UserManager
) {
    operator fun invoke() = userManager.receivers
}