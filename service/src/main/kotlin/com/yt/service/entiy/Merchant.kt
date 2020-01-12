package com.yt.service.entiy

import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table

/**
 *Create By Albert on 2019/12/27
 */
object Merchant : Table() {

    val id = long("id").autoIncrement().primaryKey()

    val createTime = datetime("create_time").defaultExpression(CurrentDateTime())

    val updateTime = datetime("update_time").defaultExpression(CurrentDateTime())

    val isDeleted = bool("is_delete").default(false)


    val merchantNo = varchar("merchant_no", 128)

    val merchantName = varchar("merchant_name", 128)

    val merchantPassWord = varchar("merchant_pass_word", 128)

}