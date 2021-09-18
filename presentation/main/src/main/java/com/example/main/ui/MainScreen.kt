package com.example.main.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.main.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    onStopService: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val isBatteryReceiverOn = remember{ mutableStateOf(true) }
    val isChargeReceiverOn = remember{ mutableStateOf(true) }
    val isMuteReceiverOn = remember{ mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .padding(25.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onStopServiceClick() },
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_power_settings_new_24),
                    contentDescription = "Power toggle"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            Card(
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.title),
                        color = Color.Green
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = stringResource(id = R.string.warning),
                        color = Color.Green
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Card(
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(id = R.string.battery_receiver)
                    )
                    Switch(
                        modifier = Modifier.weight(1f),
                        checked = isBatteryReceiverOn.value,
                        onCheckedChange = { isBatteryReceiverOn.value = it }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Card(
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(id = R.string.charge_receiver)
                    )
                    Switch (
                        modifier = Modifier.weight(1f),
                        checked = isChargeReceiverOn.value,
                        onCheckedChange = { isChargeReceiverOn.value = it }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Card(
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(5f),
                        text = stringResource(id = R.string.mute_receiver)
                    )
                    Switch(
                        modifier = Modifier.weight(1f),
                        checked = isMuteReceiverOn.value,
                        onCheckedChange = { isMuteReceiverOn.value = it }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                MainViewModel.Event.StopService -> {
                    onStopService()
                }
            }
        }
    }
}

