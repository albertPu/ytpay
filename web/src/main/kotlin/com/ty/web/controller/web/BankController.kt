package com.ty.web.controller.web

import com.ty.web.remote.ServiceFeign
import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.vo.BankListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 *Create By Albert on 2019/12/29
 */
@Controller
class BankController : BaseController() {

    @RequestMapping(WebPath.bankList, method = [RequestMethod.GET])
    fun bankList(model: Model): String {
        model.addAttribute("bankList", WebPath.bankListJson)
        model.addAttribute("bankListSave", WebPath.bankListSave)
        return "page/banklist"
    }
}

@RestController
class BankRestfulController : BaseController() {

    @Autowired
    lateinit var remoteService: ServiceFeign

    @GetMapping(WebPath.bankListJson)
    fun bankList(@RequestParam page: Int, @RequestParam limit: Int): Any {
        return remoteService.getBanks()
    }

    @PostMapping(WebPath.bankListSave)
    fun bankSave(@RequestBody bank: BankListResponse): Any {
        remoteService.saveOrUpdate(bank)
        return succees()
    }

}