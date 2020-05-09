package top.uaian.resources.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.uaian.resources.service.admin.CrawlerService;

@Controller
@RequestMapping("/crawler")
public class CrawlerController {

    private Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    @Autowired
    CrawlerService crawlerService;


    @RequestMapping("/start")
    public String start(){
        logger.info("爬虫开始运行！");
        crawlerService.CrawlerStart();
        return "爬虫开始运行！";
    }
}
