package com.ty.web.glob.interceptor

import com.yt.appcommon.WebPath
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *Create By Albert on 2019/12/28
 */
class SessionInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val session = request.getSession(false)
        val sessionId = session?.getAttribute(WebPath.sessionId)
        if (sessionId == null) {
            response.sendRedirect(request.contextPath + "/")
            return false
        }
        return super.preHandle(request, response, handler)
    }
}