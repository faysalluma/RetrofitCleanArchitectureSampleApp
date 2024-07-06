package com.groupec.retrofitcleanarchictecturesampleapp.remote.usecase

import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.ApiResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.repository.RemoteRepository
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomUseCase @Inject constructor(private val remoteRepository : RemoteRepository) {
    operator fun invoke(): Flow<NetworkResult<ApiResponse>> = remoteRepository.getRandom()
}
