package com.envel.accountservice.services

import com.envel.accountservice.domain.models.Account
import com.envel.accountservice.domain.repositories.AccountRepository
import com.envel.accountservice.exceptions.InsufficientFundException
import com.envel.accountservice.resolvers.AccountResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal
import java.util.*

@SpringJUnitConfig
internal class AccountServiceTest {

    @MockBean
   lateinit var accountRepository: AccountRepository
   var accountService:AccountService? = null

    @BeforeEach
    fun init(){
        accountService = AccountServiceImpl(accountRepository)
    }

    @Test
    fun testGetAccounts() {
     val account = getAccount()
     `when`(accountRepository.findAll()).thenReturn(listOf(account))
     val accounts = accountService?.getAccounts()
     assertNotNull(accounts)
     assertThat(accounts!!.size).isEqualTo(1)
     verify(accountRepository).findAll()
    }

    @Test
    fun getAccountById() {
     val account = getAccount()
     `when`(accountRepository.findById(anyLong())).thenReturn(Optional.of(account))
     val accountRs = accountService?.getAccountById(12)
     assertNotNull(accountRs)
     verify(accountRepository).findById(anyLong())
    }

    @Test
    fun testDeposit() {
     val account = getAccount()
     `when`(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account))
     val updatedAccount = accountService?.deposit("10123474", BigDecimal(1000))
     assertNotNull(updatedAccount)
     assertThat(updatedAccount?.correctBalance).isEqualTo(BigDecimal(1100.00))
     verify(accountRepository).findByAccountNumber(anyString())
    }

    @Test
    fun testWithdraw() {
     val account = getAccount()
     `when`(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account))
     val updatedAccount = accountService?.withdraw("10123474", BigDecimal(10))
     assertNotNull(updatedAccount)
     assertThat(updatedAccount?.correctBalance).isEqualTo(BigDecimal(90.00))
     verify(accountRepository).findByAccountNumber(anyString())
    }

 @Test
 fun testInsufficientFundWithdrawal() {
  val account = getAccount()
  `when`(accountRepository.findByAccountNumber(anyString())).thenReturn(Optional.of(account))
  assertThrows(InsufficientFundException::class.java) { accountService?.withdraw("10123474", BigDecimal(110)) }
  verify(accountRepository).findByAccountNumber(anyString())
 }

 private fun getAccount(): Account{
  return Account(1,"1234", BigDecimal(100.00))
 }


}