package com.hattrick.messenger.service.impl

import com.hattrick.messenger.config.RoomType
import com.hattrick.messenger.dto.MessageResponse
import com.hattrick.messenger.entity.Message
import com.hattrick.messenger.exception.CannotSendMessageToSelfException
import com.hattrick.messenger.exception.UserNotFoundException
import com.hattrick.messenger.mappers.messageToMessageResponse
import com.hattrick.messenger.repository.MessageRepository
import com.hattrick.messenger.service.ChatRoomService
import com.hattrick.messenger.service.MessageService
import com.hattrick.messenger.service.UserService
import java.time.LocalDateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    val userService: UserService,
    val chatRoomService: ChatRoomService,
    val messageRepository: MessageRepository
): MessageService {
    private val logger: Logger = LoggerFactory.getLogger(MessageServiceImpl::class.java)

    override fun sendMessage(from: String, to: String, text: String) {
        val fromUser = userService.findUserByUsername(from) ?: throw UserNotFoundException("User with username $from not found")
        val toUser = userService.findUserByUsername(to) ?: throw UserNotFoundException("User with username $to not found")

        require(fromUser != toUser) { CannotSendMessageToSelfException("Cannot send message to self") }

        val chatRoom = chatRoomService.fetchChatRoom(listOf(fromUser, toUser))
            ?: chatRoomService.createRoom(
                roomType = RoomType.ONE_TO_ONE,
                users = listOf(fromUser, toUser)
            )

        val message = Message(
            sender = fromUser,
            room = chatRoom,
            content = text,
        )
        messageRepository.save(message)
    }

    override fun getUnreadMessagesForUser(username: String): List<MessageResponse> {

        val user = userService.findUserByUsername(username) ?: throw UserNotFoundException("User with username $username not found")

        val chatRooms = chatRoomService.fetchChatRooms(listOf(user))

        logger.info("Found chat rooms $chatRooms"  )

        val messages = messageRepository.findInChatRoom(chatRooms)
            .filter { it.sender != user }
            .filter { it.room.roomType == RoomType.ONE_TO_ONE }

        logger.info("Found messages $messages"  )

        return messageToMessageResponse(messages)
    }

    override fun getChatHistory(user1: String, user2: String): List<Message> {
        TODO()
    }

}