package com.groupec.retrofitcleanarchictecturesampleapp.remote.model

import com.google.gson.annotations.SerializedName
import com.groupec.retrofitcleanarchictecturesampleapp.core.model.Order
import java.util.Date

data class OrderResponse(
    @SerializedName("commands")
    val commands : ArrayList<OrderItemResponse>
)
data class OrderItemResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("datecreation")
    val datecreation: Date,

    @SerializedName("fullname")
    val customerName: String,
)

fun OrderResponse.toOrderList(): List<Order> {
    return commands.map { it.toOrder() }
}

fun OrderItemResponse.toOrder(): Order {
    return Order(id = id, datecreation = datecreation, customerName = customerName)
}