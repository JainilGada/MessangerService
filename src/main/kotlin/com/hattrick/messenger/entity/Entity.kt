package com.hattrick.messenger.entity

import com.hattrick.messenger.config.RoomType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.OneToMany
import jakarta.persistence.PreUpdate
import jakarta.persistence.Version
import java.time.LocalDateTime


@MappedSuperclass
abstract class BaseEntity {

    @Column(nullable = false, updatable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    open var updatedAt: LocalDateTime = LocalDateTime.now()

    @Version
    open val version: Long = 0

    @PreUpdate
    private fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Column
    var lastLoginTime: LocalDateTime = LocalDateTime.MIN,

    @Column
    var lastLogoutTime: LocalDateTime = LocalDateTime.MIN
) : BaseEntity()

@Entity
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val roomType: RoomType,

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    val chatRoomUsers: MutableList<ChatRoomUser> = mutableListOf(),
) : BaseEntity()

@Entity
class ChatRoomUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val user: User,

    @ManyToOne
    val room: ChatRoom
) : BaseEntity()

/*
@Entity
class MessageReadStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val user: User,

    @ManyToOne
    val message: Message,

    val readAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity()
*/



@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    val sender: User,

    @ManyToOne
    val room: ChatRoom,

    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val sentAt: LocalDateTime = LocalDateTime.now(),

    /*@OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], orphanRemoval = true)
    val messageReadStatuses: MutableList<MessageReadStatus> = mutableListOf(),*/
) : BaseEntity()