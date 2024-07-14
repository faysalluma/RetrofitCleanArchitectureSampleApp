package com.groupec.retrofitcleanarchictecturesampleapp.remote.service

import com.groupec.retrofitcleanarchictecturesampleapp.remote.common.Constants
import com.groupec.retrofitcleanarchictecturesampleapp.remote.model.OrderResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.GET_ORDER)
    suspend fun getRandomDog() : Response<OrderResponse>
}