package top.uaian.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.uaian.resources.conf.ApolloConfig;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        ApolloConfig.initialize(args);
        SpringApplication.run(ApiApplication.class, args);
    }

}
