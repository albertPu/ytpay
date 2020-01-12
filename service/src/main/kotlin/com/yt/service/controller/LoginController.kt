package com.yt.service.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.vo.LoginVoRequest
import com.yt.service.entiy.Merchant
import com.yt.service.glob.WebException
import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.MerchantVO
import com.yt.service.datasoucre.MerchantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

/**
 *Create By Albert on 2019/12/29
 */
@RestController
class LoginController : BaseController() {

    @Autowired
    lateinit var loginService: MerchantService

    @RequestMapping(WebPath.serviceLoginUrl, method = [RequestMethod.POST])
    fun loginUser(@RequestBody login: LoginVoRequest, request: HttpServletRequest): Any {
        val row = loginService.getByNameAndPassWord(login.password ?: "", login.username ?: "") ?: throw WebException("用户名或密码错误")
        return succeesData(row.toTye(MerchantVO::class.java, Merchant))
    }
}