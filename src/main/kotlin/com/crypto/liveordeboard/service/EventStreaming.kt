package com.crypto.liveordeboard.service

import reactor.core.publisher.Flux

interface EventStreaming<T> {
    val event: Flux<T>
}