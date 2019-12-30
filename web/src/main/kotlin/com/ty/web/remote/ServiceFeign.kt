package com.ty.web.remote

import com.yt.appcommon.WebPath
import com.yt.appcommon.vo.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


/**
 *Create By Albert on 2019/12/29
 */
@FeignClient(value = "yt-service-1")
interface ServiceFeign {

    @PostMapping(value = [WebPath.serviceLoginUrl])
    fun getLoin(@RequestBody login: LoginVoRequest): BaseResponse<MerchantResponse?>



    @PostMapping(value = [WebPath.bankServiceList])
    fun banksList(@RequestBody(required = false) bank: BankVO?): BaseResponse<ArrayList<BankVO>>

    @PostMapping(value = [WebPath.bankServiceListSave])
    fun bankSaveOrUpdate(@RequestBody bank: BankVO)



    @PostMapping(value = [WebPath.orderServiceList])
    fun orderList(@RequestBody(required = false) order: OrderVO?): BaseResponse<ArrayList<OrderVO>>

    @PostMapping(value = [WebPath.orderServiceListSave])
    fun orderSaveOrUpdate(@RequestBody order: OrderVO)

}