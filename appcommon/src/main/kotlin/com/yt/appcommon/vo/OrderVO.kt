package com.yt.appcommon.vo

import com.yt.appcommon.annotation.DataMapping
import com.yt.appcommon.entity.PayState
import org.joda.time.DateTime

/**
 *Create By Albert on 2019/12/30
 */
class OrderVO {


    val id: Long? = null
    var orderNo: String? = null
    var orderMoney: String? = null
    var realMoney: String? = null
    var payState: PayState? = null

    @DataMapping(ignoreVo2Data = true)
    var createTime: String? = null

    var payStateDesc: String? = null

    var bankId: Long? = null
    val bankUserName: String? = null
    val bankCode: String? = null
    val bankNo: String? = null
    var bankName: String? = null
    val bankAddress: String? = null
    val todyMoney = 0.0
    val totalMoney = 0.0
    val preDayLimitMoney = 0.0
    val bankBalance: String? = null
    val bankPhoneNo: String? = null

    var bankEndNo:String?=null

    var merChantId: Long? = null
    var merchantMemberId: String? = null

    fun change() {
        payStateDesc = payState?.desc
    }
}