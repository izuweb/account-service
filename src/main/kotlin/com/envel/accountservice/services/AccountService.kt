package com.envel.accountservice.services

import com.envel.accountservice.domain.models.Account
import java.math.BigDecimal
import java.util.*

interface AccountService {

    fun getAccounts():List<Account>
    fun getAccountById(accountId: Long): Optional<Account>
    fun deposit(accountNumber: String, depositAmount:BigDecimal):Account
    fun withdraw(accountNumber: String, withdrawalAmount:BigDecimal):Account
}