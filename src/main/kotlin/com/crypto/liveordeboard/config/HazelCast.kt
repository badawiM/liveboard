package com.crypto.liveordeboard.config

import com.crypto.liveordeboard.service.OrderRepository
import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HazelCast {

    companion object {
        val hazelCastConfig: Config =
                Config().apply {
                    networkConfig.join.multicastConfig.isEnabled = false
                }
    }

    @Bean
    fun hazelcastInstance(): HazelcastInstance {
        return Hazelcast.newHazelcastInstance(hazelCastConfig)
    }
}