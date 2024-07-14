package com.groupec.retrofitcleanarchictecturesampleapp.remote.repository

import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order
import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.OrderResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.toOrder
import com.groupec.retrofitcleanarchictecturesampleapp.remote.service.ApiService
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject


class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override fun getRandom(): Flow<NetworkResult<List<Order>>> = flow {
        emit(NetworkResult.Loading) // Emit loading state
        val result = safeApiCall(
            apiCall = { apiService.getRandomDog() },
            transform = { response ->
                response.commands.map { it.toOrder() }
            }
        )
        emit(result) // Emit the result of the API call
    }.flowOn(Dispatchers.IO) // Ensure the flow runs on the IO dispatcher


    /*override fun getRandom(): Flow<NetworkResult<List<Order>>> = flow {
        try {
            val response = apiService.getRandomDog()
            if (response.isSuccessful) {
                val orders = response.body()?.commands?.map { it.toOrder() } ?: emptyList()
                emit(NetworkResult.Success(orders))
            } else {
                emit(NetworkResult.Error(HttpException(response)))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e))
        }
    }.flowOn(Dispatchers.IO)*/
}