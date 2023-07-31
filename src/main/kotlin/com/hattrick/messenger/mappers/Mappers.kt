package com.hattrick.messenger.mappers

import com.hattrick.messenger.dto.ChatHistoryResponse
import com.hattrick.messenger.dto.MessageResponse
import com.hattrick.messenger.dto.UserResponse
import com.hattrick.messenger.entity.Message
import com.hattrick.messenger.entity.User

fun mapTo(user: User): UserResponse {
    return UserResponse(
        username = user.username,
    )
}

fun messageToMessageResponse(messages: List<Message>): List<MessageResponse> {
    val groupedMessages = messages.groupBy { it.sender.username }
    return groupedMessages.map { (username, messagesBySender) ->
        val texts = messagesBySender.map { it.content }
        MessageResponse(username, texts)
    }
}

fun messageToChatHistoryResponse(messages: List<Message>): List<ChatHistoryResponse> {
    return messages.map {
        ChatHistoryResponse(
            username = it.sender.username,
            text = it.content,
        )
    }
}