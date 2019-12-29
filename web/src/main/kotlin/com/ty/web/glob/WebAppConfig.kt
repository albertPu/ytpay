package com.ty.web.glob

import com.ty.web.glob.interceptor.SessionInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 *Create By Albert on 2019/12/28
 */
@Configuration
class WebAppConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(SessionInterceptor()).addPathPatterns("/page/**")
    }

}