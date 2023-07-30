package com.hattrick.messenger.controller

import com.hattrick.messenger.entity.ChatRoom
import com.hattrick.messenger.entity.ChatRoomUser
import com.hattrick.messenger.entity.Message
import com.hattrick.messenger.entity.User
import com.hattrick.messenger.repository.ChatRoomRepository
import com.hattrick.messenger.repository.ChatRoomUserRepository
import com.hattrick.messenger.repository.MessageRepository
import com.hattrick.messenger.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/test")
class TestController(
    val messageRepository: MessageRepository,
    val userRepository: UserRepository,
    val chatRoomRepository: ChatRoomRepository,
    val chatRoomUserRepository: ChatRoomUserRepository,
    //val messageReadStatusRepository: MessageReadStatusRepository

) {
    @GetMapping("/messages")
    fun getMessages(
    ): List<Message> {
        return messageRepository.findAll()
    }

    @GetMapping("/user")
    fun getUsers(
    ): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/chatroom")
    fun getChatRooms(
    ): List<ChatRoom> {
        return chatRoomRepository.findAll()
    }

    @GetMapping("/chatroomuser")
    fun getChatRoomUser(
    ): List<ChatRoomUser> {
        return chatRoomUserRepository.findAll()
    }

    /*@GetMapping("/messageReadStatus")
    fun getMessageReadStatus(
    ): List<MessageReadStatus> {
        return messageReadStatusRepository.findAll()
    }*/
}