package com.yt.appcommon.base

import com.yt.appcommon.utils.gson
import com.yt.appcommon.vo.BaseResponse

/**
 *Create By Albert on 2019/12/28
 */
open class BaseController {
    fun succees(data: Any? = null): String {
        val result = BaseResponse<Any>()
        result.data = data
        return gson.toJson(result)
    }

    fun succeesData(data: Any? = null): BaseResponse<Any> {
        val result = BaseResponse<Any>()
        result.data = data
        return result
    }

    fun error(message: String? = null): String {
        val result = BaseResponse<Any>()
        result.message = message ?: ""
        result.success = false
        result.code = -1
        return gson.toJson(result)
    }
}