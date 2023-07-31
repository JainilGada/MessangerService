package com.hattrick.messenger.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

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