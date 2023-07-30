package com.hattrick.messenger.dto

data class RegisterRequest(val username: String, val password: String)
data class LoginRequest(val username: String, val password: String)
data class SendMessageRequest(val from: String, val to: String, val text: String)
data class SendGroupMessageRequest(val from: String, val to: String, val text: String)
data class FetchChatHistoryRequest(val user: String, val friend: String)
data class LogoutRequest(val username: String)

