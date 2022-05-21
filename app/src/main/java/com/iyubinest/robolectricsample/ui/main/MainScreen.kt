package com.iyubinest.robolectricsample.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

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
        )
        value.MapToView()
        Button(onClick = { viewModel.translate(text) }) {
            Text("Translate")
        }
    }
}