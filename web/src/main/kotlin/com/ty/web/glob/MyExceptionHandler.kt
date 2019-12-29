package com.ty.web.glob

import com.yt.appcommon.utils.gson
import com.yt.appcommon.vo.BaseResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 *Create By Albert on 2019/12/28
 */
@ControllerAdvice
class MyExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun exceptionHandler(e: Exception): String? {
        val error = BaseResponse<Any>()
        error.success = false
        error.code = -1
        if (e is WebException) {
            error.message = e.msg
        } else {
            error.message = e.cause?.toString() ?: ""
        }
        return gson.toJson(error)
    }

}

class WebException(var msg: String) : Exception() {

}