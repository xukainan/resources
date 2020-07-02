package top.uaian.resources.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * description:  <br>
 * date: 2020/7/2 16:15 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Component
@Controller
public class BasicPublisher {

     private Logger logger = LoggerFactory.getLogger(BasicPublisher.class);

     //定义JSON序列化和反序列化实例
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @GetMapping("/sendMsg")
    @ResponseBody
    public void sendMsg(@RequestParam("msg") String message){
        if(!StringUtils.isEmpty(message)) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.basic.info.exchange.name"));
//                rabbitTemplate.setRoutingKey(environment.getProperty("mq.basic.info.key.name"));
                //将字符串转换为二进制
                Message msg = MessageBuilder.withBody(message.getBytes("utf-8")).build();
                rabbitTemplate.convertAndSend(environment.getProperty("mq.basic.info.key.name"), msg);
                logger.info("发送消息：{}", message);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
