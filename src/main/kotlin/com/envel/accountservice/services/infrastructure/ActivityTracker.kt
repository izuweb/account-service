package com.envel.accountservice.services.infrastructure

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.JoinPoint
import com.envel.accountservice.domain.models.Account
import com.envel.accountservice.domain.models.AccountActivity
import com.envel.accountservice.services.AccountAuditService
import io.leangen.graphql.annotations.GraphQLMutation
import org.aspectj.lang.annotation.Aspect
import org.mountcloud.graphql.GraphqlClient
import org.mountcloud.graphql.request.mutation.DefaultGraphqlMutation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException

@Aspect
@Component
class ActivityTracker(val accountAuditService: AccountAuditService) {



    @AfterReturning(value = "execution(com.envel.accountservice.domain.models.Account " +
                   "com.envel.accountservice.services.AccountServiceImpl.*(..))&& @annotation(graphQLMutation))",
                   returning = "account")

    fun trackAccountActivity(jp: JoinPoint?, account: Account?,graphQLMutation: GraphQLMutation) {
        val  accountActivity = AccountActivity.create()
            .accountActivityId(0)
            .action(jp?.signature!!.name)
            .entity(account?.javaClass!!.simpleName)
            .resourceId(jp.args[0].toString())
            .userId("")
            .actionProperty(jp.args[1].toString()).build()

        accountAuditService.sendAccountActivityInfo(accountActivity)


    }

}