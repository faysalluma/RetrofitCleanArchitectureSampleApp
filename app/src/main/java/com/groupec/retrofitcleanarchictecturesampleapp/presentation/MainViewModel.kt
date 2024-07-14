package com.groupec.retrofitcleanarchictecturesampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order

import com.groupec.retrofitcleanarchictecturesampleapp.remote.usecase.GetRandomUseCase
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getRandomUseCase: GetRandomUseCase) : ViewModel() {
    private val _orderUiState = MutableStateFlow<RandomDogUiState>(RandomDogUiState.Loading)
    val orderUiState: StateFlow<RandomDogUiState> = _orderUiState
        /*get() = _testFlow

    private val _testFlow = getRandomUseCase().map { result ->
        when (result) {
            is NetworkResult.Loading -> RandomDogUiState.Loading
            is NetworkResult.Success -> {
                if (result.data.isEmpty()){
                    RandomDogUiState.Empty
                } else {
                    RandomDogUiState.Success(result.data)
                }
            }
            is NetworkResult.Error -> RandomDogUiState.Error( result.exception.message ?: "Retrofit Unknown error")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RandomDogUiState.Loading
    )*/

    init {
        fetchRandomDog()
    }
    fun fetchRandomDog() {
        viewModelScope.launch {
            getRandomUseCase().collect { result ->
                _orderUiState.value = when (result) {
                    is NetworkResult.Loading -> RandomDogUiState.Loading
                    is NetworkResult.Success -> {
                        if (result.data.isEmpty()){
                            RandomDogUiState.Empty
                        } else {
                            RandomDogUiState.Success(result.data)
                        }
                    }
                    is NetworkResult.Error -> RandomDogUiState.Error( result.exception.message ?: "Retrofit Unknown error")
                }
            }
        }
    }
}

sealed class RandomDogUiState {
    data object Loading : RandomDogUiState()
    data class Success(val orders: List<Order>) : RandomDogUiState()
    data class Error(val message: String) : RandomDogUiState()
    data object Empty : RandomDogUiState()
}
