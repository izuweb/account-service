package com.envel.accountservice.services

import com.envel.accountservice.domain.models.AccountActivity
import org.mountcloud.graphql.GraphqlClient
import org.mountcloud.graphql.request.mutation.DefaultGraphqlMutation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException


@Service
@Transactional
class AccountAuditServiceImpl : AccountAuditService {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${auth.token}")
    lateinit var token: String

    @Value("\${audit.url}")
    lateinit var auditUrl: String

    override fun sendAccountActivityInfo(accountActivity: AccountActivity) {
        val headers = HashMap<String,String>()
        headers[HttpHeaders.AUTHORIZATION] = "Bearer $token"
        val client = GraphqlClient.buildGraphqlClient(auditUrl)
        client.setHttpHeaders(headers)
        val mutation = DefaultGraphqlMutation("save")
        mutation.requestParameter.addObjectParameter("accountActivity",accountActivity)
        mutation.addResultAttributes("accountActivityId","action","entity","resourceId","actionProperty","userId")

        logger.info("Account activity: {}",accountActivity)
        try {
            val response = client.doMutation(mutation)
            val data = response.data
            logger.info("Response data: {}",data.toString())

        }catch (ex: IOException){
            ex.printStackTrace()
        }

    }
}