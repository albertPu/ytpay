package com.yt.service.datasoucre

import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.MerchantVO
import com.yt.service.entiy.Merchant
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

/**
 *Create By Albert on 2019/12/28
 */
@Service
class MerchantService {

    fun getByNameAndPassWord(password: String, merchantName: String): ResultRow? {
        return transaction {
            Merchant.select {
                Merchant.merchantPassWord eq password and (Merchant.merchantName eq merchantName)
            }.firstOrNull()
        }
    }

    fun getByMerchantNO(merchantNo:String):MerchantVO?{
      return  transaction {
            Merchant.select {
                Merchant.merchantNo eq  merchantNo
            }.firstOrNull()?.toTye(MerchantVO::class.java,Merchant)
        }
    }

}