package com.hattrick.messenger.service

import com.hattrick.messenger.config.RoomType
import com.hattrick.messenger.entity.ChatRoom
import com.hattrick.messenger.entity.User

interface ChatRoomService {
    fun createRoom(roomType: RoomType, users: List<User>, name: String? = null): ChatRoom

    fun fetchChatRoom(name: String): ChatRoom?

    fun fetchChatRoom(users: List<User>): ChatRoom?

    fun fetchChatRooms(users: List<User>): List<ChatRoom>

}