package top.uaian.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AdminApiApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }



}
