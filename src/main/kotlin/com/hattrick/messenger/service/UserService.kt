package com.hattrick.messenger.service

import com.hattrick.messenger.dto.UserResponse
import com.hattrick.messenger.entity.User



interface UserService {
    fun register(username: String, password: String): UserResponse
    fun login(username: String, password: String): UserResponse

    fun logout(username: String): UserResponse

    fun findUserByUsername(username: String): User?
    fun findAllUserResponse(): List<UserResponse>

    fun findAllUsers(): List<User>
}
