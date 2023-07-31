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
import jakarta.persistence.OneToMany

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
) : BaseEntity() {
    override fun toString(): String {
        return "ChatRoom(id=$id, name=$name, roomType=$roomType)"
    }
}
