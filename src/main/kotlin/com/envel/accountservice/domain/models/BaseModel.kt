package com.envel.accountservice.domain.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.leangen.graphql.annotations.GraphQLQuery
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseModel {
    @Column(name = "created_at",updatable = false)
    @GraphQLQuery(name = "createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now()
}