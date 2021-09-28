package com.crypto.liveordeboard.service

import com.crypto.liveordeboard.model.Order
import com.hazelcast.core.EntryEvent
import java.util.*


interface OrderRepository: EventStreaming<EntryEvent<Int, Order>>{
    fun getOrders(): List<Order>
    fun getOrder(orderId: Int): Order?
    fun createOrder(order: Order): Order
    fun deleteOrder(orderId: Int)
    fun getTop10BuyOrders(): List<Order>
    fun getTop10SellOrders(): List<Order>
}