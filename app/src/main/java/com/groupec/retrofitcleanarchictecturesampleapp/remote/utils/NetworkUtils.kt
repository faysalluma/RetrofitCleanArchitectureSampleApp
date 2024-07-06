package com.groupec.retrofitcleanarchictecturesampleapp.remote.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

// NetworkResult class to encapsulate the states of a network response
sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Exception) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

object NetworkUtils {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        @Suppress("UNCHECKED_CAST")
                        return@withContext NetworkResult.Success(body as T)
                    }
                }
                return@withContext NetworkResult.Error(Exception("${response.code()} ${response.message()}"))
            } catch (e: Exception) {
                return@withContext NetworkResult.Error(Exception(e))
            }
        }
    }
}