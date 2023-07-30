package com.hattrick.messenger.controller

import com.hattrick.messenger.config.Constants
import com.hattrick.messenger.dto.LoginRequest
import com.hattrick.messenger.dto.LogoutRequest
import com.hattrick.messenger.dto.RegisterRequest
import com.hattrick.messenger.dto.Response
import com.hattrick.messenger.dto.SendMessageRequest
import com.hattrick.messenger.entity.Message
import com.hattrick.messenger.exception.CannotSendMessageToSelfException
import com.hattrick.messenger.exception.UserAlreadyExistException
import com.hattrick.messenger.exception.UserNotFoundException
import com.hattrick.messenger.service.MessageService
import com.hattrick.messenger.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val userService: UserService,
    private val messageService: MessageService
) {

    @PostMapping("/create/user")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<*> {
        return respondWith { userService.register(request.username, request.password) }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<*> {
        return respondWith { userService.login(request.username, request.password) }
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: LogoutRequest): ResponseEntity<*> {
        return respondWith { userService.logout(request.username) }
    }

    @GetMapping("/users")
    fun getUsers(
    ): ResponseEntity<*> {
        return respondWith { userService.findAllUsers().map { it.username } }
    }

    @PostMapping("/send-message")
    fun sendMessage(@RequestBody request: SendMessageRequest): ResponseEntity<*> {
        return respondWith {
            messageService.sendMessage(
                from = request.from,
                to = request.to,
                text = request.text
            )
        }
    }

    @GetMapping("/unread-messages/{username}")
    fun getUnreadMessages(@PathVariable username: String): ResponseEntity<*> {
        return respondWith { messageService.getUnreadMessagesForUser(username)}
    }

    @GetMapping("/chat-history")
    fun getChatHistory(
        @RequestParam senderUsername: String,
        @RequestParam receiverUsername: String
    ): List<Message> {
        TODO()
    }

    fun <T> respondWith(block: () -> T): ResponseEntity<Response<T>> {
        return try {
            val result = block()
            ResponseEntity.ok(Response(status = Constants.SUCCESS, data = result))
        } catch (e: UserAlreadyExistException) {
            ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Response(status = Constants.FAILURE, message = e.message))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response(status = Constants.FAILURE, message = e.message))
        } catch (e: CannotSendMessageToSelfException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response(status = Constants.FAILURE, message = e.message))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response(status = Constants.FAILURE, message = "Something unexpected went wrong"))
        }
    }

}


