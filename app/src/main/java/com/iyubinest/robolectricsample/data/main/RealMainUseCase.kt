package com.iyubinest.robolectricsample.data.main

import com.iyubinest.robolectricsample.data.UseCaseResponse
import com.iyubinest.robolectricsample.data.UseCaseResponse.Error
import com.iyubinest.robolectricsample.data.UseCaseResponse.Success
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RealMainUseCase(
    private val client: OkHttpClient = OkHttpClient.Builder().build(),
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build(),
) : MainUseCase {

    private data class RealMainUseCaseData(
        val id: String,
        val translated: String,
    )

    private val url: String = "https://6286cb6d7864d2883e79cf96.mockapi.io/translate/translate/1"

    override suspend fun execute(request: MainUseCase.MainUseCaseRequest) =
        doRequest().let { MainUseCase.MainUseCaseResponse(it) }

    private suspend fun doRequest(): UseCaseResponse<MainUseCase.MainUseCaseData> =
        suspendCoroutine { continuation ->
            client.newCall(url.asRequest()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseString = response.body.string()
                    if (responseString.isNotEmpty()) {
                        val data: RealMainUseCaseData? = moshi.asData(responseString)
                        if (data != null) {
                            val successData = MainUseCase.MainUseCaseData(data.translated)
                            continuation.resume(Success(successData))
                        } else {
                            continuation.resumeError()
                        }
                    } else {
                        continuation.resumeError()
                    }
                }
            })
        }

    private fun Continuation<Error>.resumeError(e: Exception? = null) =
        resume(Error(e?.message ?: "Error getting translation"))


    private fun String.asRequest() = Request.Builder()
        .get()
        .url(this)
        .build()

    private fun Moshi.asData(responseString: String) = adapter(RealMainUseCaseData::class.java)
        .fromJson(responseString)
}



