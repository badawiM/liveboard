package com.crypto.liveordeboard.service

import com.crypto.liveordeboard.model.Order
import com.crypto.liveordeboard.model.OrderDraft
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors


/**
 * methods below should handle the different scenarios of failure to return meaningful error to the caller
 */
@Component
class OrderService(
        val orderRepository: OrderRepository
) {

    fun getOrders(): List<Order>{
        return orderRepository.getOrders()
                .sortedByDescending { it.orderDateTime }
    }

    fun getOrder(orderId: Int): Order? {
        return orderRepository.getOrder(orderId)!!
    }

    fun createOrder(orderDraft: OrderDraft): Order {
        val order = orderDraft.toOrder()
        return orderRepository.createOrder(order)
    }

    fun updateOrder(order: Order): Order {
        return order
    }

    fun deleteOrder(orderId: Int) {
        orderRepository.deleteOrder(orderId)
    }


}