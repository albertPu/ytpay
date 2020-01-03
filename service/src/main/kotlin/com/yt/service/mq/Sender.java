package com.yt.service.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 通过延迟消息插件发动延迟消息
     * @param
     * @param expiration
     */
    public void sendDelayMessageByPlugins(Object sendMessage,Long expiration){
        //消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        //绑定异步监听回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("成功");
            } else {
                System.out.println("失败");
            }
        });
        rabbitTemplate.convertAndSend(RabbitConfig.DELAY_GROUP_ACTIVITTY_EXCHANGE,
                RabbitConfig.DELAY_GROUP_ACTIVITTY_ROUTING_KEY, sendMessage,(message)->{
            System.out.println("过期时间"+expiration);
            message.getMessageProperties().setHeader("x-delay", expiration);//设置延迟时间
            return message;
        });

    }
}
