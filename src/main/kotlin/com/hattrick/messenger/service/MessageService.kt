package com.hattrick.messenger.service

import com.hattrick.messenger.dto.ChatHistoryResponse
import com.hattrick.messenger.dto.MessageResponse
import com.hattrick.messenger.entity.Message

interface MessageService{
    fun sendMessage(from: String, to: String, text: String)
    fun getUnreadMessagesForUser(username: String): List<MessageResponse>
    fun getChatHistory(user1: String, user2: String): List<ChatHistoryResponse>

}
