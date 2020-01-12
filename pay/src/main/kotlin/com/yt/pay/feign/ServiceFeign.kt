package com.yt.pay.feign

import com.yt.appcommon.WebPath
import com.yt.appcommon.vo.BaseResponse
import com.yt.appcommon.vo.OrderVO
import com.yt.appcommon.vo.SMSMsgRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import javax.ws.rs.POST

@FeignClient(value = "yt-service-1")
interface ServiceFeign {

    @GetMapping(WebPath.payServiceBank)
    fun payBank(@RequestParam merchantNo: String, @RequestParam money: String, @RequestParam merChantMemberId: String): BaseResponse<OrderVO>

    @GetMapping(WebPath.payServiceQueryOrder)
    fun queryOrder(@RequestParam orderNO: String): BaseResponse<OrderVO>

    @PostMapping(WebPath.payServiceSuccess)
    fun paySuccess(@RequestBody  msg: SMSMsgRequest): BaseResponse<OrderVO>

}