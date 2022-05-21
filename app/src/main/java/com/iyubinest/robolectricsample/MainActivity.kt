package com.iyubinest.robolectricsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.iyubinest.robolectricsample.ui.main.MainScreen
import com.iyubinest.robolectricsample.ui.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                MainScreen()
            }
        }
    }
}
