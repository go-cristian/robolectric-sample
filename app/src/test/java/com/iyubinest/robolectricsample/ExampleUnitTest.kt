package com.iyubinest.robolectricsample

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.iyubinest.robolectricsample.ui.main.MainScreen
import com.iyubinest.robolectricsample.ui.main.MainViewModel
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = ShadowTestApp::class, packageName = "com.luckytrader.app")
class MainViewTests : BaseComponentTest() {

    @MockK
    lateinit var mainViewModel: MainViewModel
    lateinit var state: MutableStateFlow<MainViewModel.State>

    @Test
    fun `translates properly`() {
        state = MutableStateFlow<MainViewModel.State>(MainViewModel.State.Loading)
        every { mainViewModel.state } returns state
        every { mainViewModel.translate(any()) } answers  {
            state = MutableStateFlow<MainViewModel.State >(MainViewModel.State.Loading)
        }
        testView { MainScreen(mainViewModel) }
        composeTestRule.onNodeWithTag("field").performTextInput("Hola Mundo!")
        composeTestRule.onNodeWithTag("Hello World!").assertExists()
    }
}