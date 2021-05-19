package com.envel.accountservice.domain.models
import io.leangen.graphql.annotations.GraphQLQuery
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "ACCOUNTS",schema = "account")
data class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID", nullable = false)
    @GraphQLQuery(name = "accountId")
    var accountId: Long,

    @Column(name = "ACCOUNT_NUMBER")
    @GraphQLQuery(name = "accountNumber")
    var accountNumber: String? = null,

    @Column(name = "CORRECT_BALANCE")
    @GraphQLQuery(name = "correctBalance")
    var correctBalance: BigDecimal? = BigDecimal.ZERO
) : BaseModel() {
    constructor():this(0,"")
}