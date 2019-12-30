package com.yt.appcommon.vo

import com.yt.appcommon.entity.PayState

/**
 *Create By Albert on 2019/12/30
 */
class OrderVO {


    val id: Int? = 0
    val orderNo: String? = null
    val orderMoney: String? = null
    val payState: PayState? = null

    var payStateDesc: String? = null

    val bankId: Int? = null
    val userName: String? = null
    val bankCode: String? = null
    val bankNo: String? = null
    val bankName: String? = null
    val bankAddress: String? = null
    val todyMoney = 0.0
    val totalMoney = 0.0
    val preDayLimitMoney = 0.0
    val bankBalance: String? = null
    val bankPhoneNo: String? = null

    fun change() {
        payStateDesc = payState?.desc
    }
}