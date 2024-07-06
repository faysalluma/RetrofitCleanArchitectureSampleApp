package com.groupec.retrofitcleanarchictecturesampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.ApiResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.usecase.GetRandomUseCase
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getRandomUseCase: GetRandomUseCase) : ViewModel() {
    private val _randomUiState = MutableStateFlow<RandomDogUiState>(RandomDogUiState.Loading)
    val randomUiState: StateFlow<RandomDogUiState> = _randomUiState

    init {
        fetchRandomDog()
    }
    private fun fetchRandomDog() {
        viewModelScope.launch {
            getRandomUseCase().collect { result ->
                    _randomUiState.value = when (result) {
                        is NetworkResult.Loading -> RandomDogUiState.Loading
                        is NetworkResult.Success -> RandomDogUiState.Success(result.data)
                        is NetworkResult.Error -> RandomDogUiState.Error( result.exception.message ?: "Unknown error")
                    }
                }
        }
    }
}

sealed class RandomDogUiState {
    data object Loading : RandomDogUiState()
    data class Success(val apiResponse: ApiResponse) : RandomDogUiState()
    data class Error(val message: String) : RandomDogUiState()
    data object Empty : RandomDogUiState()
}