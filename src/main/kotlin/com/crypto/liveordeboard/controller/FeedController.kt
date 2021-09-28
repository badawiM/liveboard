package com.crypto.liveordeboard.controller

import com.crypto.liveordeboard.model.Summary
import com.crypto.liveordeboard.service.OrderSummaryStreamingService
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/stream")
class FeedController(val orderStreamingService: OrderSummaryStreamingService) {

    @GetMapping(path = ["order-summary"], produces = [TEXT_EVENT_STREAM_VALUE])
    fun getOrderSummaryFeed(): Flux<ServerSentEvent<Summary>> {
        return Flux.create { sink ->
            val sub = orderStreamingService.stream.subscribe {
                sink.next(it)
            }
            sink.onDispose {
                sub.dispose()
            }
        }

    }
}