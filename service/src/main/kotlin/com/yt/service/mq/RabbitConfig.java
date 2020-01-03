package com.yt.service.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    // 订单超时延时交换机
    public static final String DELAY_GROUP_ACTIVITTY_EXCHANGE = "DELAY_GROUP_ACTIVITTY_EXCHANGE";

    // 订单超时队列
    public static final String DELAY_GROUP_ACTIVITTY_QUEUE = "DELAY_GROUP_ACTIVITTY_QUEUE";

    // 订单路由键
    public static final String DELAY_GROUP_ACTIVITTY_ROUTING_KEY = "DELAY_GROUP_ACTIVITTY_ROUTING_KEY";

    // 创建一个超时消费队列
    @Bean
    public Queue delayGroupActivityQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue(DELAY_GROUP_ACTIVITTY_QUEUE, true);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public CustomExchange delayGroupActivityExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_GROUP_ACTIVITTY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
     * 将交换机和队列绑定
     *
     * @return
     */
    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(delayGroupActivityQueue()).to(delayGroupActivityExchange())
                .with(DELAY_GROUP_ACTIVITTY_ROUTING_KEY).noargs();
    }
}
