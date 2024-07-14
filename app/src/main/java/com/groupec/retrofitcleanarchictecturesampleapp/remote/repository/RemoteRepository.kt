package com.groupec.retrofitcleanarchictecturesampleapp.remote.repository

import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order
import com.groupec.retrofitcleanarchictecturesampleapp.remote.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RemoteRepository{
    fun getRandom() : Flow<NetworkResult<List<Order>>>
}