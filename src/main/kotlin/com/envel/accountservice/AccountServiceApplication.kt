package com.envel.accountservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class AccountServiceApplication

fun main(args: Array<String>) {
    runApplication<AccountServiceApplication>(*args)
}
