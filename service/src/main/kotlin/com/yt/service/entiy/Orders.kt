package com.yt.service.entiy

import com.yt.appcommon.entity.PayState


/**
 *Create By Albert on 2019/12/30
 */
object Orders : BaseTable() {

    val id = integer("id").autoIncrement().primaryKey()

    val orderNo = varchar("order_no", 128)

    val orderMoney = decimal("order_money", 36, 2)

    val bankId = integer("bank_id")

    val payState = enumeration("pay_state", PayState::class)
}

