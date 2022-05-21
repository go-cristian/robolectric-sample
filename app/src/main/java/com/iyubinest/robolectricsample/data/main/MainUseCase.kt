package com.iyubinest.robolectricsample.data.main

import com.iyubinest.robolectricsample.data.UseCaseResponse

interface MainUseCase {

    data class MainUseCaseData(val translation: String)
    data class MainUseCaseRequest(val term: String)
    data class MainUseCaseResponse(val response: UseCaseResponse<MainUseCaseData>)

    suspend fun execute(request: MainUseCaseRequest): MainUseCaseResponse
}