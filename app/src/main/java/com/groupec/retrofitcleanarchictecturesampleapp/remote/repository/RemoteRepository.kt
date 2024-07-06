package com.groupec.retrofitcleanarchictecturesampleapp.remote.repository

import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.ApiResponse
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RemoteRepository{
    fun getRandom() : Flow<NetworkResult<ApiResponse>>
}