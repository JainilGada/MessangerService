package com.hattrick.messenger.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
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
) : BaseEntity() {
    override fun toString(): String {
        return "User(id=$id, username='$username'))"
    }
}
