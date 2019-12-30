package com.yt.appcommon.entity

import com.yt.appcommon.base.BaseEnum

/**
 *Create By Albert on 2019/12/30
 */
enum class PayState(var value: Int, var desc: String) {
    UNPAY(0, "未支付"), PAIED(1, "已支付"), CANCEL(2, "已取消")
}