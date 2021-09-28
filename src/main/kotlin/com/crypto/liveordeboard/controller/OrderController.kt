package com.crypto.liveordeboard.controller

import com.crypto.liveordeboard.model.Order
import com.crypto.liveordeboard.model.OrderDraft
import com.crypto.liveordeboard.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


/**
 * endpoints below should return different ResponseEntity status with better error handling
 */
@RestController
@RequestMapping("/rest/order")
class OrderController(val orderService: OrderService) {


    @GetMapping
    fun getOrders(): ResponseEntity<List<Order>>{
        return ResponseEntity.ok(orderService.getOrders())
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Int): ResponseEntity<*>{
        return ResponseEntity.ok(orderService.getOrder(orderId))
    }

    @PostMapping
    fun createOrder(@RequestBody orderDraft: OrderDraft): ResponseEntity<*> {
         return ResponseEntity.ok(orderService.createOrder(orderDraft))

    }

    @PutMapping
    fun updateOrder(@RequestBody order: Order): ResponseEntity<*> {
        return ResponseEntity.ok(orderService.updateOrder(order))
    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable orderId: Int): ResponseEntity<*> {
        orderService.deleteOrder(orderId)
        return  ResponseEntity.noContent().build<Unit>()
    }


}