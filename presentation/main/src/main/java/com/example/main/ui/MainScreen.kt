package com.example.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.main.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MainScreen(
    onStopService: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
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
            Text(text = "Welcome to H.E.V. ...")

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

