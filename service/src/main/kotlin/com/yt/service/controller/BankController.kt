package com.yt.service.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.vo.BankListResponse
import com.yt.service.web.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 *Create By Albert on 2019/12/29
 */
@RestController
class BankController : BaseController() {

    @Autowired
    lateinit var bankService: BankService

    @RequestMapping(WebPath.serviceBankList, method = [RequestMethod.POST])
    fun bankList(): Any {
        val row = bankService.getBankList()
        return succeesData(row)
    }

    @RequestMapping(WebPath.serviceBankListSave, method = [RequestMethod.POST])
    fun bankSave(@RequestBody bank: BankListResponse): Any {
        bankService.saveOrUpdate(bank)
        return succeesData()
    }


}