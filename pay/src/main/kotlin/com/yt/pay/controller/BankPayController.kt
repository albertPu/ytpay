package com.yt.pay.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.entity.PayState
import com.yt.appcommon.vo.SMSMsgRequest
import com.yt.pay.feign.ServiceFeign
import com.yt.pay.glob.WebException
import org.apache.commons.lang.time.DateFormatUtils
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@Controller
class BankPayController : BaseController() {

    @Autowired
    lateinit var serviceFeign: ServiceFeign

    @GetMapping(WebPath.payByBank)
    fun bankPay(@RequestParam orderNo: String, viewModel: Model): String {
        val orderVO = serviceFeign.queryOrder(orderNo)
        if (!orderVO.success) {
            throw WebException(orderVO.message)
        }

        val createDate = DateTime.parse(orderVO.data?.createTime)
        val diffTime = System.currentTimeMillis() - (createDate?.toDate()?.time ?: 0L)
        val remainTime = 10 * 60 - (diffTime / 1000)
        val payDesc = if (orderVO.data?.payState == PayState.PAIED) "恭喜您,改单已支付" else "改单已失效"
        viewModel.addAttribute("payTimeOut", remainTime < 0)
        viewModel.addAttribute("payMoney", orderVO.data?.orderMoney)
        viewModel.addAttribute("payUser", orderVO.data?.bankUserName)
        viewModel.addAttribute("payBankNo", orderVO.data?.bankNo)
        viewModel.addAttribute("payBankName", orderVO.data?.bankName)
        viewModel.addAttribute("payDesc", payDesc)
        viewModel.addAttribute("payTime", remainTime)
        return "bankpay"
    }
}

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class BankPayRestController : BaseController() {

    @Autowired
    lateinit var serviceFeign: ServiceFeign

    @GetMapping(WebPath.payByPreBank)
    fun payBankPre(@RequestParam merchantNo: String, @RequestParam merChantMemberId: String, @RequestParam money: String): Any {
        val orderVO = serviceFeign.payBank(merchantNo, money, merChantMemberId)
        if (!orderVO.success) throw WebException(orderVO.message)
        return orderVO
    }

    @PostMapping(WebPath.payBySuccess)
    fun paySuccess(@RequestBody msg:SMSMsgRequest): Any {
        val orderVO = serviceFeign.paySuccess(msg)
        if (!orderVO.success) throw WebException(orderVO.message)
        return orderVO
    }
}