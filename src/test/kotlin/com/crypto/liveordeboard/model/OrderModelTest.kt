package com.crypto.liveordeboard.model

import com.crypto.liveordeboard.buyOrders
import com.crypto.liveordeboard.sellOrders
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.random.Random

internal class OrderModelTest{


    @Test
    fun `given list has value aggregate by price should return result`(){
        val ordersSummary: List<OrderSummary> = buyOrders.aggByPrice()

        ordersSummary shouldHaveSize 2
        ordersSummary shouldContainExactly  listOf(
                OrderSummary(pricePerCoin = "1.00".toBigDecimal(), total = "3.00".toBigDecimal()),
                OrderSummary(pricePerCoin = "2.00".toBigDecimal(), total = "4.00".toBigDecimal())
        )
    }

    @Test
    fun `summary can return buy and sell`(){
        val summary = Summary.from(ordersBuy = buyOrders, ordersSell = sellOrders)

        summary.buyList shouldHaveSize 2
        summary.buyList shouldContainInOrder listOf(
                OrderSummary(pricePerCoin = "2.00".toBigDecimal(), total = "4.00".toBigDecimal()),
                OrderSummary(pricePerCoin = "1.00".toBigDecimal(), total = "3.00".toBigDecimal())
        )

        summary.sellList shouldHaveSize 2
        summary.sellList shouldContainInOrder listOf(
                OrderSummary(pricePerCoin = "1.00".toBigDecimal(), total = "3.00".toBigDecimal()),
                OrderSummary(pricePerCoin = "2.00".toBigDecimal(), total = "4.00".toBigDecimal())
        )
    }

    @Test
    fun `OrderDraft can return order`(){
        val order = OrderDraft(
                userId = "test",
                coinType = CoinType.Bitcoin,
                quantity = 1,
                pricePerCoin = "1.00".toBigDecimal(),
                orderType = OrderType.BUY
        ).toOrder()

        order.userId shouldBe "test"
        order.coinType shouldBe CoinType.Bitcoin
        order.quantity shouldBe 1
        order.pricePerCoin shouldBe "1.00".toBigDecimal()
        order.orderType shouldBe OrderType.BUY
    }


}