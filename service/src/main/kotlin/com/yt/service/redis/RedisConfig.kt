package com.yt.service.redis

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory?): RedisTemplate<String, Any> { // 创建RedisTemplate<String, Object>对象
        val template = RedisTemplate<String, Any>()
        // 配置连接工厂
        template.setConnectionFactory(factory!!)
        // 定义Jackson2JsonRedisSerializer序列化对象
        val jacksonSeial = Jackson2JsonRedisSerializer(Any::class.java)
        val om = ObjectMapper()
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jacksonSeial.setObjectMapper(om)
        val stringSerial = StringRedisSerializer()
        // redis key 序列化方式使用stringSerial
        template.keySerializer = stringSerial
        // redis value 序列化方式使用jackson
        template.valueSerializer = jacksonSeial
        // redis hash key 序列化方式使用stringSerial
        template.hashKeySerializer = stringSerial
        // redis hash value 序列化方式使用jackson
        template.hashValueSerializer = jacksonSeial
        template.afterPropertiesSet()
        return template
    }
}