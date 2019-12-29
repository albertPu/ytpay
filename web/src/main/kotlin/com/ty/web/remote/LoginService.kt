package com.ty.web.remote

import com.yt.appcommon.WebPath
import com.yt.appcommon.vo.BaseResponse
import com.yt.appcommon.vo.LoginVoRequest
import com.yt.appcommon.vo.MerchantResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


/**
 *Create By Albert on 2019/12/29
 */
@FeignClient(value = "yt-service-1")
interface LoginService {

    @PostMapping(value = [WebPath.serviceLoginUrl])
    fun getLoin(@RequestBody login: LoginVoRequest): BaseResponse<MerchantResponse?>
}