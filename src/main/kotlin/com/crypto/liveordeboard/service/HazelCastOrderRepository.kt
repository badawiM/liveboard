package com.crypto.liveordeboard.service

import com.crypto.liveordeboard.model.Order
import com.crypto.liveordeboard.model.OrderType
import com.hazelcast.core.EntryEvent
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.map.listener.EntryAddedListener
import com.hazelcast.map.listener.EntryRemovedListener
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

@Component
class HazelCastOrderRepository(hazelcastInstance: HazelcastInstance) : OrderRepository {

    val repo = hazelcastInstance.getMap<Int, Order>(CACHE_NAME)

    override val event = Flux.create<EntryEvent<Int, Order>> { sink ->

        val addEntryListener = repo.addEntryListener(
                EntryAddedListener<Int, Order> {
                    println(LocalDateTime.now())
                           sink.next(it)
                },
                false)

        val entryRemovedListener = repo.addEntryListener(
                EntryRemovedListener<Int, Order> {
                    sink.next(it)
        }, false)

        sink.onDispose {
            repo.removeEntryListener(addEntryListener)
            repo.removeEntryListener(entryRemovedListener)
        }
    }
    .share()
    .log()


    companion object {
        const val CACHE_NAME = "orders"
    }


    override fun getOrders(): List<Order> {
        return repo.entries.map { it.value }
    }

    override fun getOrder(orderId: Int): Order? {
        return repo[orderId]
    }

    override fun createOrder(order: Order): Order {
        repo[order.orderId] = order
        return order
    }

    override fun deleteOrder(orderId: Int) {
        repo.remove(orderId)
    }

    override fun getTop10BuyOrders(): List<Order> {
        return repo.entries
                .asSequence()
                .filter {it.value.orderType == OrderType.BUY}
                .map { it.value }
                .sortedByDescending { it.orderDateTime }
                .take(10)
                .toList()
    }

    override fun getTop10SellOrders(): List<Order> {
        return repo.entries
                .asSequence()
                .filter {it.value.orderType == OrderType.SELL}
                .map { it.value }
                .sortedByDescending { it.orderDateTime }
                .take(10)
                .toList()
    }

}