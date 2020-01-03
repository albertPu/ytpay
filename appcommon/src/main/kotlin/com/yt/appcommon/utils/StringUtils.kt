package com.yt.appcommon.utils

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {


    fun getOrderIdByTime(merchantNo: String): String {
        val sdf = SimpleDateFormat("yyyyMMddHHmmss");
        val newDate = sdf.format(Date());
        var result = "";
        val random = Random();
        result += merchantNo
        for (i in 0..3) {
            result += random.nextInt(10);
        }
        return newDate + result
    }

    fun getRealMoney(money: Double): Double {
        val random = Random().nextInt(200).toDouble() / 100
        return money - random
    }

}