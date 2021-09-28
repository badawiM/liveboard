package com.crypto.liveordeboard

import com.crypto.liveordeboard.model.CoinType
import com.crypto.liveordeboard.model.Order
import com.crypto.liveordeboard.model.OrderType
import java.time.LocalDateTime

val buyOrder1 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.BUY,
        orderDateTime = LocalDateTime.now()
)

val buyOrder2 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.BUY,
        orderDateTime = LocalDateTime.now().minusMinutes(1)
)

val buyOrder3 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.BUY,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val buyOrder4 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "2.00".toBigDecimal(),
        orderType = OrderType.BUY,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val buyOrder5 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "2.00".toBigDecimal(),
        orderType = OrderType.BUY,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val sellOrder1 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.SELL,
        orderDateTime = LocalDateTime.now()
)

val sellOrder2 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.SELL,
        orderDateTime = LocalDateTime.now().minusMinutes(1)
)

val sellOrder3 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "1.00".toBigDecimal(),
        orderType = OrderType.SELL,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val sellOrder4 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "2.00".toBigDecimal(),
        orderType = OrderType.SELL,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val sellOrder5 = Order(
        orderId = 1,
        userId = "test",
        coinType = CoinType.Bitcoin,
        quantity = 1,
        pricePerCoin = "2.00".toBigDecimal(),
        orderType = OrderType.SELL,
        orderDateTime = LocalDateTime.now().minusMinutes(2)
)

val buyOrders: List<Order> = listOf(buyOrder1, buyOrder2, buyOrder3, buyOrder4, buyOrder5)
val sellOrders: List<Order> = listOf(sellOrder1, sellOrder2, sellOrder3, sellOrder4, sellOrder5)