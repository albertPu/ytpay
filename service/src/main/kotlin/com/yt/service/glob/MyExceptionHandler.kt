package com.yt.service.glob

import com.yt.appcommon.vo.BaseResponse
import com.yt.appcommon.utils.gson
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
    fun exceptionHandler(e: Exception): BaseResponse<Any>? {
        val error = BaseResponse<Any>()
        error.success = false
        error.code = -1
        if (e is WebException) {
            error.message = e.msg
        } else {
            error.message = e.message ?: ""
        }
        e.printStackTrace()
        return error
    }

}

class WebException(var msg: String) : Exception() {

}