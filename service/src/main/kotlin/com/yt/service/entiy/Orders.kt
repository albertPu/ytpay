package com.yt.service.entiy

import com.yt.appcommon.entity.PayState
import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table


/**
 *Create By Albert on 2019/12/30
 */
object Orders : Table() {

    val id = long("id").autoIncrement().primaryKey()

    val createTime = datetime("create_time").defaultExpression(CurrentDateTime())

    val updateTime = datetime("update_time").defaultExpression(CurrentDateTime())

    val isDeleted = bool("is_delete").default(false)



    val orderNo = varchar("order_no", 128)

    val orderMoney = decimal("order_money", 36, 2)

    val realMoney = decimal("real_pay_money", 36, 2)

    val bankId = long("bank_id") references Banks.id

    val merChantId = long("merchant_id") references Merchant.id

    val merchantMemberId = varchar("merchant_member_Id", 256)

    val payState = enumeration("pay_state", PayState::class)


}

