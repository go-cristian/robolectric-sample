package com.iyubinest.robolectricsample

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import com.iyubinest.robolectricsample.data.UseCaseResponse
import com.iyubinest.robolectricsample.data.main.MainUseCase
import com.iyubinest.robolectricsample.ui.main.MainScreen
import com.iyubinest.robolectricsample.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = ShadowTestApp::class, packageName = "com.luckytrader.app")
class MainViewTests : BaseComponentTest() {

    @MockK
    lateinit var mainUseCase: MainUseCase

    @Test
    fun `translates properly`() {
        coEvery { mainUseCase.execute(any()) } returns MainUseCase.MainUseCaseResponse(
            UseCaseResponse.Success(MainUseCase.MainUseCaseData("Hello World!"))
        )
        testView { MainScreen(remember { MainViewModel(mainUseCase) }) }
        composeTestRule.onNodeWithTag("field").performTextInput("Hola Mundo!")
        composeTestRule.onNodeWithTag("translate-cta").performClick()
        composeTestRule.onNodeWithTag("result").assertTextContains("Hello World!")
        composeTestRule.onNodeWithText("Hello World!").assertExists()
    }

    @Test
    fun `disables button if no text`() {
        composeTestRule.onNodeWithTag("field").performTextInput("")
        composeTestRule.onNodeWithTag("translate-cta").assertIsNotEnabled()
    }
}