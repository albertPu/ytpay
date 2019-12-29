package com.ty.web.controller.web

import com.yt.appcommon.WebPath
import com.yt.appcommon.vo.MerchantResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

/**
 *Create By Albert on 2019/12/28
 */
@Controller
class HomeController {

    @RequestMapping(WebPath.homePage, method = [RequestMethod.GET])
    fun index(model: Model, httpRequest: HttpServletRequest): String {
        val user = httpRequest.session?.getAttribute(WebPath.sessionId) as? MerchantResponse
        model.addAttribute("user", user)
        return "index"
    }

    @RequestMapping(WebPath.welcomePage, method = [RequestMethod.GET])
    fun welcome(): String {
        return "page/welcome"
    }
}