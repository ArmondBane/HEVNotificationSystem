package com.example.main.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.device.manager.UserManager
import com.example.core.device.receiver.HEVReceiver
import com.example.core.domain.usecase.GetReceiversUseCase
import com.example.core.domain.usecase.ToggleReceiverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getReceiversUseCase: GetReceiversUseCase,
    private val toggleReceiverUseCase: ToggleReceiverUseCase
) :ViewModel(), LifecycleObserver {

    val receivers = getReceiversUseCase()

    fun onToggleReceiver(receiverKey: String) {
        toggleReceiverUseCase(receiverKey)
    }

    private val eventChannel = Channel<Event>()
    val event = eventChannel.receiveAsFlow()

    fun onStopServiceClick() = viewModelScope.launch {
        eventChannel.send(Event.StopService)
    }

    sealed class Event {
        object StopService: Event()
    }
}