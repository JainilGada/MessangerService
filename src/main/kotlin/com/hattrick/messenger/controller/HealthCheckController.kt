package com.hattrick.messenger.controller


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/healthcheck")
class HealthCheckController{
    @GetMapping("/ping")
    fun ping(): ResponseEntity<*> = ResponseEntity.ok("pong")
}