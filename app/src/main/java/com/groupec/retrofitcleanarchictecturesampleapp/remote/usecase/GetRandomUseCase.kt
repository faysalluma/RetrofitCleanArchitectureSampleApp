package com.groupec.retrofitcleanarchictecturesampleapp.remote.usecase

import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order
import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.OrderResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.repository.RemoteRepository
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRandomUseCase @Inject constructor(private val remoteRepository : RemoteRepository) {
    operator fun invoke(): Flow<NetworkResult<List<Order>>> = remoteRepository.getRandom()
}
