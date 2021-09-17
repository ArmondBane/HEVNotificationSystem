package com.example.main.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) :ViewModel(), LifecycleObserver {

    private val eventChannel = Channel<Event>()
    val event = eventChannel.receiveAsFlow()

    fun onStopServiceClick() = viewModelScope.launch {
        eventChannel.send(Event.StopService)
    }

    sealed class Event {
        object StopService: Event()
    }
}