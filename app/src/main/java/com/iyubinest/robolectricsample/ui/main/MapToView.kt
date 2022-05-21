package com.iyubinest.robolectricsample.ui.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun MainViewModel.State.MapToView() {
    when (this) {
        is MainViewModel.State.Error -> Text(text = "Error: $cause")
        is MainViewModel.State.Loading -> Text(text = "Loading...")
        is MainViewModel.State.Idle -> {}
        is MainViewModel.State.Success -> Text(text = value.term, Modifier.testTag("result"))
    }
}
