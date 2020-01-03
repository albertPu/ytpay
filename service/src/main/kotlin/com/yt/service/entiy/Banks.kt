package com.yt.service.entiy

import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table


/**
 *Create By Albert on 2019/12/29
 */
object Banks : Table() {

    val id = long("id").autoIncrement().primaryKey()

    val createTime = datetime("create_time").defaultExpression(CurrentDateTime())

    val updateTime = datetime("update_time").defaultExpression(CurrentDateTime())

    val isDeleted = bool("is_delete").default(false)


    val bankUserName = varchar("user_name", 128)
    val bankCode = varchar("bank_code", 128)
    val bankNo = varchar("bank_no", 128)
    val bankName = varchar("bank_name", 128)
    val bankAddress = varchar("bank_address", 128)
    val todyMoney = decimal("tody_money", 36, 2)
    val totalMoney = decimal("total_money", 36, 2)
    val bankBalance = varchar("bank_balance", 128)
    val bankPhoneNo = varchar("bank_phone_no", 128)
    val preDayLimitMoney = decimal("pre_day_limit_money", 36, 2)
}