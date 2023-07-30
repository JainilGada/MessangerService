package com.hattrick.messenger.dto

data class Response<T> (
    val status: String? = null,
    val message: String? = null,
    val data: T? = null
)

data class UserResponse(
    val username: String,
)

data class MessageResponse(
    val username: String,
    val text: List<String>,
)