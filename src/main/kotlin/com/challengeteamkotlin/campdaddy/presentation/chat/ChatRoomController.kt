package com.challengeteamkotlin.campdaddy.presentation.chat

import com.challengeteamkotlin.campdaddy.application.chat.ChatRoomService
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomDetailResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/chatroom")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {
    @PostMapping
    fun createChat(@RequestBody request: CreateChatRoomRequest): ResponseEntity<Unit> {
        val id = chatRoomService.createChat(request)

        return ResponseEntity.created(URI.create(String.format("/api/v1/chatroom/%d", id))).build()
    }

    @GetMapping("/me/{memberId}")
    fun getChatList(
        @PathVariable memberId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<ChatRoomResponse>> {
        val chatList = chatRoomService.getPersonalChatList(memberId, userPrincipal)

        return ResponseEntity.ok().body(chatList)
    }

    @GetMapping("/{roomId}")
    fun getChat(
        @PathVariable roomId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<ChatRoomDetailResponse> {
        val chatList = chatRoomService.getChatDetail(roomId, userPrincipal)

        return ResponseEntity.ok().body(chatList)
    }

    @DeleteMapping("/{roomId}")
    fun removeChat(
        @PathVariable roomId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        chatRoomService.removeChat(roomId, userPrincipal)

        return ResponseEntity.noContent().build()
    }
}