package com.groupec.retrofitcleanarchictecturesampleapp.remote.repository

import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.ApiResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.service.ApiService
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkUtils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override fun getRandom(): Flow<NetworkResult<ApiResponse>> = flow {
        emit(NetworkResult.Loading) // Emit loading state
        val result = safeApiCall { apiService.getRandomDog() }
        emit(result) // Emit the result of the API call
    }.flowOn(Dispatchers.IO) // Ensure the flow runs on the IO dispatcher

    /*override fun getRandom(): Flow<NetworkResult<ApiResponse>> {
        return flow {
            try {
                val response = apiService.getRandomDog()
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()!!))
                } else {
                    emit(NetworkResult.Error(Exception("Failed Load Data")))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }*/
}