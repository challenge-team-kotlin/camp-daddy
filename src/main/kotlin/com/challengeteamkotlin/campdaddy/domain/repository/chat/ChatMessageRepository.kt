package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatMessageJpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository: ChatMessageJpaRepository {

}
