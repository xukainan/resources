package top.uaian.admin_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"top.uaian.*"})
public class AdminApiApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }



}
