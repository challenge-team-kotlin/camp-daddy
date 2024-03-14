package com.challengeteamkotlin.campdaddy.common.config.chat

import com.challengeteamkotlin.campdaddy.common.config.chat.handler.ClientMessageInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig(
    private val clientMessageInterceptor: ClientMessageInterceptor,
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableSimpleBroker("/sub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*")
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(clientMessageInterceptor)
    }


}