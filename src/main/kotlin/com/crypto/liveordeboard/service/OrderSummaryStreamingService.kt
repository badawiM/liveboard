package com.crypto.liveordeboard.service

import com.crypto.liveordeboard.model.*
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random


@Component
class OrderSummaryStreamingService(val hazelCastOrderRepository: HazelCastOrderRepository) {

    final val stream = Flux.create<ServerSentEvent<Summary>> { sink ->
        val sub = hazelCastOrderRepository.event
                .subscribe {
                    sink.next(createEvent())
                }
        sink.next(createEvent())
        sink.onDispose {
            sub.dispose()
        }
    }
    .log()
    .cache(1)



    internal fun createEvent(): ServerSentEvent<Summary>{
        return ServerSentEvent.builder<Summary>()
                .id(LocalDateTime.now().toString())
                .event("OrdersSummary")
                .data(Summary.from( ordersBuy = hazelCastOrderRepository.getTop10BuyOrders(), ordersSell = hazelCastOrderRepository.getTop10SellOrders()))
                .retry(Duration.ofSeconds(5))
                .build()
    }

}