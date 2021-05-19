package com.envel.accountservice.services

import com.envel.accountservice.domain.models.Account
import com.envel.accountservice.domain.repositories.AccountRepository
import com.envel.accountservice.exceptions.InsufficientFundException
import com.envel.accountservice.exceptions.ResourceNotFoundException
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
@GraphQLApi
@Transactional
class AccountServiceImpl(val accountRepository: AccountRepository) : AccountService {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GraphQLQuery(name = "accounts")
    override fun getAccounts(): List<Account> {
       return accountRepository.findAll()
    }

    @GraphQLQuery(name = "account")
    override fun getAccountById(@GraphQLArgument(name = "accountId") accountId: Long): Optional<Account> {
        return  accountRepository.findById(accountId)
    }

   @GraphQLMutation(name="deposit")
    override fun deposit(@GraphQLArgument(name = "accountNumber")accountNumber: String, @GraphQLArgument(name = "depositAmount")depositAmount: BigDecimal):Account {
        val account = getAccount(accountNumber)
        val newBalance =account.correctBalance!!.add(depositAmount)
        account.correctBalance = newBalance
        return account
    }

    @GraphQLMutation(name="withdraw")
    override fun withdraw(@GraphQLArgument(name = "accountNumber")accountNumber: String, @GraphQLArgument(name = "withdrawalAmount")withdrawalAmount: BigDecimal):Account {
        val account = getAccount(accountNumber)
        if (account.correctBalance!!.minus(withdrawalAmount).toInt() <=0){
            throw InsufficientFundException("Not sufficient fund for this transaction")
        }
        else{
            val newBalance = account.correctBalance!!.minus(withdrawalAmount)
            account.correctBalance = newBalance
        }
        return account
    }


    private fun getAccount(accountNumber: String):Account{
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow {
                ResourceNotFoundException(
                    accountNumber.toString(), Account::accountNumber.name,
                    "Cannot find account with accountNumber $accountNumber"
                )
            }
    }
}