package com.iyubinest.robolectricsample.ui.main

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iyubinest.robolectricsample.data.UseCaseResponse
import com.iyubinest.robolectricsample.data.main.MainUseCase
import com.iyubinest.robolectricsample.data.main.RealMainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class MainViewModel(
    private val mainUseCase: MainUseCase = RealMainUseCase(),
) : ViewModel() {

    sealed class State {
        @Parcelize
        data class Error(val cause: String?) : State(), Parcelable

        @Parcelize
        object Loading : State(), Parcelable

        @Parcelize
        object Idle : State(), Parcelable

        @Parcelize
        class Success(val value: MainViewData) : State(), Parcelable
    }

    @Parcelize
    data class MainViewData(val term: String) : Parcelable

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State>
        get() = _state

    fun translate(value: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val response = mainUseCase.execute(value.mapTo())
            _state.value = response.mapTo()
        }
    }

    private fun String.mapTo() = MainUseCase.MainUseCaseRequest(this)

    private fun MainUseCase.MainUseCaseResponse.mapTo(): State = when (response) {
        is UseCaseResponse.Error -> State.Error(response.msg)
        is UseCaseResponse.Success -> State.Success(MainViewModel.MainViewData(response.value.translation))
    }
}