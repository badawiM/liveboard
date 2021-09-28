package com.crypto.liveordeboard.service

import com.crypto.liveordeboard.buyOrders
import com.crypto.liveordeboard.model.Order
import com.crypto.liveordeboard.model.OrderSummary
import com.crypto.liveordeboard.model.Summary
import com.crypto.liveordeboard.sellOrders
import com.hazelcast.core.EntryEvent
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.codec.ServerSentEvent
import reactor.test.StepVerifier
import reactor.test.publisher.TestPublisher
import java.time.*
import kotlin.random.Random


internal class OrderSummaryStreamingServiceTest{

    private val hazelCastOrderRepository: HazelCastOrderRepository = mock(HazelCastOrderRepository::class.java)
    private val orderSummaryStreamingService = OrderSummaryStreamingService(hazelCastOrderRepository)
    lateinit var testPublisher: TestPublisher<EntryEvent<Int, Order>>



    @BeforeEach
    fun setup(){
        testPublisher = TestPublisher.create()
        `when`(hazelCastOrderRepository.event).thenReturn(testPublisher.flux())
    }

    @Test
    fun `when first subscribe receive current orderSummary`(){

        StepVerifier
                .create(orderSummaryStreamingService.stream)
                .assertNext{
                    it.data() shouldBe sse.data()
                }
                .thenCancel()
                .verify()
    }

    @Test
    fun `when repository emit then order summary emit`(){

        StepVerifier
                .create(orderSummaryStreamingService.stream)
                .then { testPublisher.emit() }
                .assertNext{
                    it.data() shouldBe sse.data()
                }
                .thenCancel()
                .verify()

    }

    @Test
    fun `test createEvent`(){
        `when`(hazelCastOrderRepository.getTop10BuyOrders()).thenReturn(buyOrders)
        `when`(hazelCastOrderRepository.getTop10SellOrders()).thenReturn(sellOrders)

        val sse = orderSummaryStreamingService.createEvent()
        sse.data() shouldBe Summary(
                buyList = listOf(
                        OrderSummary(total="4.00".toBigDecimal(), pricePerCoin="2.00".toBigDecimal()),
                        OrderSummary(total="3.00".toBigDecimal(), pricePerCoin="1.00".toBigDecimal())
                ),
                sellList = listOf(
                        OrderSummary(total="3.00".toBigDecimal(), pricePerCoin="1.00".toBigDecimal()),
                        OrderSummary(total="4.00".toBigDecimal(), pricePerCoin="2.00".toBigDecimal())
                )
        )

        sse.event() shouldBe "OrdersSummary"
        sse.retry() shouldBe Duration.ofSeconds(5)
    }

    private val sse = ServerSentEvent.builder<Summary>()
            .event("OrdersSummary")
            .data(Summary.from( ordersBuy = emptyList(), ordersSell = emptyList()))
            .retry(Duration.ofSeconds(5))
            .build()
}