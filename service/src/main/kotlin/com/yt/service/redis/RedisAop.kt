package com.yt.service.redis

import com.yt.appcommon.utils.gson
import com.yt.service.annotation.Operate
import com.yt.service.annotation.RedisCache
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.Duration
import java.time.temporal.ChronoUnit


@Aspect
@Component
class RedisAop {

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    /**
     * 指定切点
     * 匹配 被添加LogAnnotation标注的方法
     */
    @Pointcut("@annotation(redisCache)")
    fun pointCut(redisCache: RedisCache) {
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param joinPoint
     * @return
     */
    @Around("pointCut(redisCache)")
    fun around(joinPoint: ProceedingJoinPoint, redisCache: RedisCache): Any? {

        var applId: String? = null
        val args = joinPoint.args
        if (args != null && args.isNotEmpty()) {
            applId = try {
                gson.toJson(args[0]).replace("\"", "").replace(":", "")
                        .replace("{", "").replace("}", "")
            } catch (e: Exception) {
                args[0].toString()
            }
        }


        //获取目标方法所在类
        val target = joinPoint.target.toString()
        val className = target.split("@".toRegex()).toTypedArray()[0]

        val classAnnotation = joinPoint.target::class.java.getAnnotation(RedisCache::class.java)
        val classNameSpace = classAnnotation?.nameSpace ?: ""


        val nameSpace = redisCache.nameSpace
        val cacheTime = redisCache.cacheTimeSecond
        val operate = redisCache.operate


        //获取目标方法的方法名称
        val methodName = joinPoint.signature.name
        val returnType = (joinPoint.signature as? MethodSignature)?.returnType

        //redis中key格式：
        val redisKey: String = (if (StringUtils.isEmpty(classNameSpace + nameSpace)) className else classNameSpace + nameSpace) + applId.toString() + methodName

        val obj = stringRedisTemplate.opsForValue().get(redisKey)
        if (obj != null) {
            return gson.fromJson(obj, returnType)
        }

        val o = joinPoint.proceed()
        if (o != null) {
            stringRedisTemplate.opsForValue().set(redisKey, gson.toJson(o), Duration.of(cacheTime, ChronoUnit.SECONDS))
        }
        if (operate != Operate.QUERY) {
            deleteCache(classNameSpace + nameSpace)
        }
        return o

    }

    fun deleteCache(nameSpace: String) {
        val prefix = "$nameSpace*"
        // 获取所有的key
        val keys = stringRedisTemplate.keys(prefix)
        stringRedisTemplate.delete(keys)
    }
}