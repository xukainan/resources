package top.uaian.resources.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crawler")
public class CrawlerController {

    private Logger logger = LoggerFactory.getLogger(CrawlerController.class);


    @RequestMapping("/start")
    public String start(){
        return "爬虫开始运行！";
    }
}
