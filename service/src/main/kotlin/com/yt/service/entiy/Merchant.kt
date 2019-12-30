package com.yt.service.entiy

import org.jetbrains.exposed.sql.Table

/**
 *Create By Albert on 2019/12/27
 */
object Merchant : BaseTable() {

    val id = integer("id").autoIncrement().primaryKey()

    val merchantNo = varchar("merchant_no", 128)

    val merchantName = varchar("merchant_name", 128)

    val merchantPassWord = varchar("merchant_pass_word", 128)

}