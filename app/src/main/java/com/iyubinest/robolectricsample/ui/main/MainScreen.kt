package com.iyubinest.robolectricsample.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MainScreen(
    viewModel: MainViewModel = remember { MainViewModel() }
) {
    val value = viewModel.state.collectAsState().value
    var text by remember { mutableStateOf("") }
    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") },
            maxLines = 1,
            modifier = Modifier.testTag("field"),
        )
        value.MapToView()
        Button(
            modifier = Modifier.testTag("translate-cta"),
            onClick = { viewModel.translate(text) },
            enabled = value is MainViewModel.State.Loading || text.isNotEmpty()
        ) {
            Text("Translate")
        }
    }
}