package top.uaian.resources.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * description:  <br>
 * date: 2020/7/2 15:24 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Configuration
public class RabbitConfig{
    //定义读取配置文件环境变量的实例
    @Autowired
    private Environment environment;

    private Logger logger = LoggerFactory.getLogger(RabbitConfig.class);
    //链接工厂实例
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    //消息监听器所在的容器工厂配置类实例
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer;

    //单一消费者实例
    @Bean("simpleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(cachingConnectionFactory);
        //设置消息传输的格式，这里是JSON
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(1);
        // 设置并发消费者的最大数量
        factory.setMaxConcurrentConsumers(1);
        // 设置每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        return factory;
    }

    //多个消费者实例，针对高并发
    @Bean("multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        simpleRabbitListenerContainerFactoryConfigurer.configure(factory, cachingConnectionFactory);
        //设置消息传输的格式，这里是JSON
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息的确认消费模式，这里不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        // 设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(10);
        // 设置并发消费者的最大数量
        factory.setMaxConcurrentConsumers(15);
        // 设置每个实例拉取的消息数量
        factory.setPrefetchCount(10);
        return factory;
    }

    //配置发送消息的操作组件RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(){
        //设置发送消息后进行确认
        cachingConnectionFactory.setPublisherConfirms(true);
        //设置发送消息后返回确认信息
        cachingConnectionFactory.setPublisherReturns(true);
        //构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //发送消息成功后。输出成功信息
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            logger.info("消息发送成功：CorrelationData({}), ack({}), cause({})", correlationData, ack, cause);
        });
        //发送消息失败后。输出失败信息
        rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange,
                                          String routingKey) -> {
            logger.info("消息丢失：message({}), replyCode({}), replyText({}), exchange({}), routingKey({})", message,
                    replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;

    }

    /** 创建简单的消息模型：队列、交换机和路由**/
    //队列
    @Bean("basicQueue")
    public Queue basicQueue(){
        return new Queue(environment.getProperty("mq.basic.info.queue.name"),true);
    }
    //交换机
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(environment.getProperty("mq.basic.info.exchange.name"),false,true);
    }

    //绑定
    @Bean
    public Binding basicBuilder(){
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(environment.getProperty("mq.basic.info" +
                ".key.name"));
    }

}
