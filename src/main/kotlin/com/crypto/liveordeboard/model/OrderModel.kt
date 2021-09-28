package com.crypto.liveordeboard.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.random.Random

interface OrderStructure {
    val userId: String
    val coinType: CoinType
    val quantity: Int
    val pricePerCoin: BigDecimal
    val orderType: OrderType
}

data class Order(
        val orderId: Int,
        override val userId: String,
        override val coinType: CoinType,
        override val quantity: Int,
        override val pricePerCoin: BigDecimal,
        override val orderType: OrderType,
        val orderDateTime: LocalDateTime
        ): OrderStructure, Serializable

data class OrderDraft(
        override val userId: String,
        override val coinType: CoinType,
        override val quantity: Int,
        override val pricePerCoin: BigDecimal,
        override val orderType: OrderType
): OrderStructure {

    fun toOrder(): Order {
        return Order(
                orderId = Random.nextInt(),
                userId = userId,
                coinType = coinType,
                quantity = quantity,
                pricePerCoin = pricePerCoin,
                orderType = orderType,
                orderDateTime = LocalDateTime.now()
        )
    }
}

data class Summary(
        val buyList: List<OrderSummary>,
        val sellList: List<OrderSummary>
): Serializable {

    companion object {
        fun from(ordersBuy: List<Order>, ordersSell: List<Order>) : Summary{
            return Summary(
                    buyList = ordersBuy.aggByPrice().sortedByDescending { it.pricePerCoin },
                    sellList = ordersSell.aggByPrice().sortedBy { it.pricePerCoin }
            )
        }
    }
}

data class OrderSummary(
        val total: BigDecimal,
        val pricePerCoin: BigDecimal
) : Serializable


enum class OrderType{
    BUY, SELL
}

enum class CoinType{
    Litecoin, Ethereum, Bitcoin
}


fun List<Order>.aggByPrice() =
    this.groupBy { it.pricePerCoin }
            .map {
                OrderSummary(
                        pricePerCoin = it.key,
                        total = it.value.sumOf { o -> o.pricePerCoin.multiply(o.quantity.toBigDecimal()) }
                )
            }
