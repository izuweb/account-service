package com.envel.accountservice.domain.models

data class AccountActivity
    (
    var accountActivityId: Long? = null,
    var action: String? = null,
    var entity: String? = null,
    var resourceId: String? = null,
    var actionProperty: String? = null,
    var userId: String? = ""
)
{
        fun action(action: String) = apply { this.action = action }
        fun entity(entity: String) = apply { this.entity = entity }
        fun resourceId(resourceId: String) = apply { this.resourceId = resourceId }
        fun actionProperty(actionProperty: String) = apply { this.actionProperty = actionProperty }
        fun accountActivityId(accountActivityId: Long) = apply { this.accountActivityId = accountActivityId }
        fun userId(userId: String) = apply { this.userId = userId }
        fun build() = AccountActivity(accountActivityId,action, entity, resourceId, actionProperty)

        companion object{
            fun create() = AccountActivity()
        }
}