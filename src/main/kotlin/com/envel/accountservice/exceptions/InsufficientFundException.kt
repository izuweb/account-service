package com.envel.accountservice.exceptions

class InsufficientFundException(override var message: String): RuntimeException(message)