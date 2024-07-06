package com.groupec.retrofitcleanarchictecturesampleapp.remote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Exception) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { return@withContext NetworkResult.Success(it) }
            }
            return@withContext NetworkResult.Error(Exception("${response.code()} ${response.message()}"))
        } catch (e: Exception) {
            return@withContext NetworkResult.Error(Exception(e))
        }
    }
}