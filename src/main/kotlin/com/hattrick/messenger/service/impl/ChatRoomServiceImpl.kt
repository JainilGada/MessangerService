package com.hattrick.messenger.service.impl

import com.hattrick.messenger.config.RoomType
import com.hattrick.messenger.entity.ChatRoom
import com.hattrick.messenger.entity.ChatRoomUser
import com.hattrick.messenger.entity.User
import com.hattrick.messenger.repository.ChatRoomRepository
import com.hattrick.messenger.service.ChatRoomService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ChatRoomServiceImpl(
    val chatRoomRepository: ChatRoomRepository,
) : ChatRoomService {

    private val logger: Logger = LoggerFactory.getLogger(ChatRoomServiceImpl::class.java)

    override fun createRoom(roomType: RoomType, users: List<User>, name: String?): ChatRoom {
        logger.info("Creating chat room with roomType $roomType, users $users, name $name")
        val chatRoom = ChatRoom(
            name = name,
            roomType = roomType
        )
        users.forEach() {
            chatRoom.chatRoomUsers.add(ChatRoomUser(user = it, room = chatRoom))
        }
        return chatRoomRepository.save(chatRoom)
    }

    override fun fetchChatRoom(name: String): ChatRoom? {
        return chatRoomRepository.findByName(name)
    }

    override fun fetchChatRoom(users: List<User>): ChatRoom? {
        val chatRooms = chatRoomRepository.findByRoomType(RoomType.ONE_TO_ONE)
        return chatRooms.find { chatRoom ->
            chatRoom.chatRoomUsers.map(ChatRoomUser::user).containsAll(users)
        }
    }

    override fun fetchChatRooms(users: List<User>): List<ChatRoom> {
        val chatRooms = chatRoomRepository.findByRoomType(RoomType.ONE_TO_ONE)
        return chatRooms.filter { chatRoom ->
            chatRoom.chatRoomUsers.map(ChatRoomUser::user).containsAll(users)
        }
    }
}