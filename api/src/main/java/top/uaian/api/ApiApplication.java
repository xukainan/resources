package top.uaian.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.uaian.api.conf.ApolloConfig;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        ApolloConfig.initialize(args);
        SpringApplication.run(ApiApplication.class, args);
    }

}
