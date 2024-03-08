package com.challengeteamkotlin.campdaddy.presentation.chat

import com.challengeteamkotlin.campdaddy.application.chat.ChatRoomService
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomDetailResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import org.springframework.http.ResponseEntity
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
    fun getChatList(@PathVariable memberId: Long): ResponseEntity<List<ChatRoomResponse>> {
        val chatList = chatRoomService.getPersonalChatList(memberId)

        return ResponseEntity.ok().body(chatList)
    }

    @GetMapping("/{roomId}")
    fun getChat(@PathVariable roomId: Long): ResponseEntity<ChatRoomDetailResponse> {
        val chatList = chatRoomService.getChat(roomId)

        return ResponseEntity.ok().body(chatList)
    }

    @DeleteMapping("/{roomId}")
    fun removeChat(@PathVariable roomId: Long): ResponseEntity<Unit> {
        chatRoomService.removeChat(roomId)

        return ResponseEntity.noContent().build()
    }
}