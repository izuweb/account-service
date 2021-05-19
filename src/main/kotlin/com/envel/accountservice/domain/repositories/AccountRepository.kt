package com.envel.accountservice.domain.repositories;

import com.envel.accountservice.domain.models.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountNumber(accountNumber: String):Optional<Account>
}