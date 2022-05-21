package com.iyubinest.robolectricsample

import com.iyubinest.robolectricsample.data.UseCaseResponse
import com.iyubinest.robolectricsample.data.main.MainUseCase
import com.iyubinest.robolectricsample.data.main.RealMainUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.cgomez.testrecordinterceptor.TestFileSaverInterceptor
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = ShadowTestApp::class, packageName = "com.luckytrader.app")
class MainUseCaseTests {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(TestFileSaverInterceptor("cached"))
        .build()
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Test
    fun `parses the info`() = runTest {
        val mainUseCase = RealMainUseCase(okHttpClient, moshi)
        val request = MainUseCase.MainUseCaseRequest("Hola Mundo!")
        val response =
            mainUseCase.execute(request).response as UseCaseResponse.Success<MainUseCase.MainUseCaseData>
        Assert.assertEquals(response.value.translation, "Hello World!")
    }
}