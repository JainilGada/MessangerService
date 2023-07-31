package com.hattrick.messenger.repository

import com.hattrick.messenger.config.RoomType
import com.hattrick.messenger.entity.ChatRoom
import com.hattrick.messenger.entity.ChatRoomUser
import com.hattrick.messenger.entity.Message
import com.hattrick.messenger.entity.User
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsernameAndPassword(username: String, password: String): User?
    fun findByUsername(username: String): User?
}

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findByRoomType(roomType: RoomType): List<ChatRoom>
    fun findByName(name: String): ChatRoom?
}
@Repository
interface MessageRepository : JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.room IN :rooms AND m.createdAt > :createdAt")
    fun findInChatRoomAndCreatedAtAfter(rooms: List<ChatRoom>, createdAt: LocalDateTime): List<Message>

    @Query("SELECT m FROM Message m WHERE m.room IN :rooms")
    fun findInChatRoom(rooms: List<ChatRoom>): List<Message>

}
