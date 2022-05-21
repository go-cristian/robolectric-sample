package com.iyubinest.robolectricsample

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.iyubinest.robolectricsample.ui.theme.Theme
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.robolectric.shadows.ShadowLog

open class BaseComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    fun print() {
        composeTestRule.onRoot().printToLog("Testing")
    }

    internal fun testView(component: @Composable () -> Unit) {
        composeTestRule.setContent {
            Theme {
                component()
            }
        }
    }

    fun onText(text: String) = composeTestRule.onNodeWithText(text)
}