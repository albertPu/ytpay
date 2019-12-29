package com.yt.service.web

import com.yt.service.entiy.Merchant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

/**
 *Create By Albert on 2019/12/28
 */
@Component
class LoginService {

    fun loginByNameAndPassWord(password: String, merchantName: String): ResultRow? {
        return transaction {
            Merchant.select {
                Merchant.merchantPassWord eq password and (Merchant.merchantName eq merchantName)
            }.firstOrNull()
        }
    }

}