package com.ty.web.controller.web

import com.yt.appcommon.base.BaseController
import com.ty.web.glob.WebException
import com.ty.web.remote.ServiceFeign
import com.yt.appcommon.WebPath
import com.yt.appcommon.vo.LoginVoRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 *Create By Albert on 2019/12/28
 */
@Controller
class LoginController : BaseController() {


    @RequestMapping("/")
    fun login(model: Model): String {
        model.addAttribute("loginUrl", WebPath.loginUrl)
        model.addAttribute("homPage", WebPath.homePage)
        return "page/login"
    }


}

@RestController
class LoginRestfulController : BaseController() {

    @Autowired
    var loginService: ServiceFeign? = null

    @PostMapping(WebPath.loginUrl)
    fun loginUser(@RequestBody login: LoginVoRequest, request: HttpServletRequest): String {
        val response = loginService?.getLoin(login) ?: throw WebException("用户名或密码错误")
        request.session?.setAttribute(WebPath.sessionId, response.data)
        return succees()
    }
}

