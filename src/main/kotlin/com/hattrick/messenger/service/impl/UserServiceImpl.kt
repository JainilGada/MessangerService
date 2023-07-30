package com.hattrick.messenger.service.impl

import com.hattrick.messenger.dto.UserResponse
import com.hattrick.messenger.entity.User
import com.hattrick.messenger.exception.UserAlreadyExistException
import com.hattrick.messenger.exception.UserNotFoundException
import com.hattrick.messenger.mappers.mapTo
import com.hattrick.messenger.repository.UserRepository
import com.hattrick.messenger.service.UserService
import java.time.LocalDateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    override fun register(username: String, password: String): UserResponse {
        if (userRepository.findByUsername(username) != null) {
            throw UserAlreadyExistException("User already exists")
        }
        val user = userRepository.save(User(username = username, password = password))
        logger.info("User with username $username registered at ${user.createdAt}")
        return user.let { mapTo(it) }
    }

    override fun login(username: String, password: String): UserResponse {
        val user = userRepository.findByUsernameAndPassword(username, password)
            ?: throw UserNotFoundException("User with username $username not found")
        user.lastLoginTime = LocalDateTime.now();
        logger.info("User with username $username logged in at ${user.lastLoginTime}")
        return userRepository.save(user).let { mapTo(it) }
    }

    override fun logout(username: String): UserResponse {
        val user = userRepository.findByUsername(username)
            ?: throw UserNotFoundException("User with username $username not found")
        user.lastLogoutTime = LocalDateTime.now();
        logger.info("User with username $username logged out at ${user.lastLogoutTime}")
        return userRepository.save(user).let { mapTo(it) }
    }

    override fun findUserByUsername(username: String): User? {
        val user = userRepository.findByUsername(username)
        return user
    }

    override fun findAllUserResponse(): List<UserResponse> {
        logger.info("fetching all users")
        return findAllUsers().map { mapTo(it) }
    }
    override fun findAllUsers(): List<User> {
        return userRepository.findAll()
    }
}

