package com.yt.service.mq

import com.rabbitmq.client.Channel
import com.yt.appcommon.entity.PayState
import com.yt.service.datasoucre.OrderService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.charset.StandardCharsets

@Component
class Receiver {

    @Autowired
    lateinit var orderService: OrderService


    @RabbitListener(queues = [RabbitConfig.DELAY_GROUP_ACTIVITTY_QUEUE])
    @Throws(IOException::class)
    operator fun get(message: Message, channel: Channel) {
        channel.basicAck(message.messageProperties.deliveryTag, false)
        val orderNo = String(message.body, StandardCharsets.UTF_8)
        val orderVO = orderService.getByOrderNo(orderNo)
        if (orderVO!=null){
            orderVO.payState =PayState.CANCEL
            orderService.saveOrUpdate(orderVO)
        }

    }
}