package com.groupec.retrofitcleanarchictecturesampleapp.remote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Exception) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

suspend fun <T, R> safeApiCall(apiCall: suspend () -> Response<T>, transform: (T) -> R): NetworkResult<R> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@withContext NetworkResult.Success(transform(it))
                }?:return@withContext NetworkResult.Error(Exception("Empty response body"))
            }
            return@withContext NetworkResult.Error(HttpException(response))
        } catch (e: Exception) {
            return@withContext NetworkResult.Error(Exception(e))
        }
    }
}