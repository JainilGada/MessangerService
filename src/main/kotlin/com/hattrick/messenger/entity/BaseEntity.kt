package com.hattrick.messenger.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
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
