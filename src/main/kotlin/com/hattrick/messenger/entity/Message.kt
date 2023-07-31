package com.hattrick.messenger.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

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

    @OneToMany(mappedBy = "message", cascade = [CascadeType.ALL], orphanRemoval = true)
    val messageReadStatuses: MutableList<MessageReadStatus> = mutableListOf()
) : BaseEntity() {
    override fun toString(): String {
        return "Message(id=$id, sender=${sender.username}, content='$content', sentAt=$sentAt)"
    }
}