package com.yt.service.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.entity.PayState
import com.yt.appcommon.utils.StringUtils
import com.yt.appcommon.vo.OrderVO
import com.yt.service.glob.WebException
import com.yt.service.datasoucre.BankService
import com.yt.service.datasoucre.MerchantService
import com.yt.service.datasoucre.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class PayController : BaseController() {

    @Autowired
    lateinit var merchantService: MerchantService

    @Autowired
    lateinit var bankService: BankService

    @Autowired
    lateinit var orderService: OrderService

    @GetMapping(WebPath.payServiceBank)
    fun prePay(@RequestParam merchantNo: String, @RequestParam money: String, @RequestParam merChantMemberId: String): Any {

        val merchantVO = merchantService.getByMerchantNO(merchantNo) ?: throw WebException("商户编号不存")

        val bank = bankService.getBankList().firstOrNull {
            it.todyMoney + money.toDouble() <= it.preDayLimitMoney
        } ?: throw WebException("没有可用的银行卡")


        var randomMoney = StringUtils.getRealMoney(money.toDouble())

        val order = orderService.getByRealMoneyAndBankCode(randomMoney.toBigDecimal(), bank.bankNo
                ?: "", bank.bankName
                ?: "")

        if (randomMoney == order?.realMoney?.toDouble()) {
            randomMoney += 0.01
        }

        val orderVO = OrderVO()
        orderVO.bankId = bank.id
        orderVO.merChantId = merchantVO.id
        orderVO.realMoney = money
        orderVO.orderMoney = randomMoney.toString()
        orderVO.merchantMemberId = merChantMemberId
        orderVO.orderNo = StringUtils.getOrderIdByTime(merchantVO.merchantNo)
        orderVO.payState = PayState.UNPAY

        orderService.saveOrUpdate(orderVO)
        val payOrder = orderService.getByOrderNo(orderVO.orderNo!!)
        return succeesData(payOrder)

    }


    @GetMapping(WebPath.payServiceQueryOrder)
    fun queryOrder(@RequestParam orderNO: String): Any {
        val orderVO = orderService.getByOrderNo(orderNO)
        return succeesData(orderVO)
    }
}