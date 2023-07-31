package com.hattrick.messenger.exception

class UserAlreadyExistException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)
class CannotSendMessageToSelfException(message: String) : RuntimeException(message)