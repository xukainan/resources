package top.uaian.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.uaian.resources.mq.BasicPublisher;

/**
 * description:  <br>
 * date: 2020/7/3 14:13 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMQTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BasicPublisher basicPublisher;

    @Test
    public void test1(){
        String msg = "hello world!";
        basicPublisher.sendMsg(msg);
    }
}
