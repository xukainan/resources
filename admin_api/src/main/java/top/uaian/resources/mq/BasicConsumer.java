package top.uaian.resources.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * description:  <br>
 * date: 2020/7/2 16:24 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Component
public class BasicConsumer {

    private Logger logger = LoggerFactory.getLogger(BasicConsumer.class);

    //定义JSON序列化和反序列化实例
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.basic.info.queue.name}", containerFactory = "simpleListenerContainer")
    public void consumeMsg(@Payload byte[] msg) {
        try {
            System.out.println(msg);
            String message = new String(msg, "utf-8");
            logger.info("消费消息：{}", message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
