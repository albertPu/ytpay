package com.ty.web.controller.web

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 *Create By Albert on 2019/12/28
 */
@Controller
class AppErrorController : ErrorController {

    @RequestMapping("/error")
    fun error(): String {
        return "page/404"
    }

    override fun getErrorPath(): String {
        return "page/404"
    }
}