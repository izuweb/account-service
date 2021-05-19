package com.envel.accountservice.services

import com.envel.accountservice.domain.models.Account
import com.envel.accountservice.domain.models.AccountActivity

interface AccountAuditService {

   fun sendAccountActivityInfo(accountActivity: AccountActivity)
}