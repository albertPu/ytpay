package com.yt.pay.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class BankPayController:BaseController() {


    @GetMapping(WebPath.payByBank)
    fun bankPay(@RequestParam money:String,viewModel:Model):String{
        viewModel.addAttribute("payMoney",money)
        viewModel.addAttribute("payUser","张先绪")
        viewModel.addAttribute("payBankNo","622290209900")
        viewModel.addAttribute("payBankName","工商银行")
        viewModel.addAttribute("payTime",60*10)
        return "bankpay"
    }
}