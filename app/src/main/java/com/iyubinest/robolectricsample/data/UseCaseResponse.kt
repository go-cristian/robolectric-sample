package com.iyubinest.robolectricsample.data

sealed class UseCaseResponse<out T : Any> {
    data class Success<out T : Any>(val value: T) : UseCaseResponse<T>()
    data class Error(val msg: String) : UseCaseResponse<Nothing>()
}
