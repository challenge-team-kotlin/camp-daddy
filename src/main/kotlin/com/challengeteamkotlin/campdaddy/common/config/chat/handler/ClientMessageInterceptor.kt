package com.challengeteamkotlin.campdaddy.common.config.chat.handler

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.member.exception.AccessDeniedException
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class ClientMessageInterceptor(
    private val jwtPlugin: JwtPlugin,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        if (accessor.command == StompCommand.CONNECT) {
            val accessToken = accessor.getFirstNativeHeader("Authorization") as String
            jwtPlugin.validateToken(accessToken)
                .onFailure { throw AccessDeniedException(AuthErrorCode.ACCESS_DENIED) }
        }

        return message
    }
}
