package top.uaian.resources.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.uaian.model.inner.crawler.CrawlerWebsite;
import top.uaian.model.output.JsonResult;
import top.uaian.resources.service.admin.CrawlerWebsiteService;

import java.util.List;

/**
 * description:  <br>
 * date: 2020/5/7 16:09 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@RequestMapping("/crawler/website")
@RestController
public class CrawlerWebsiteController {

    @Autowired
    CrawlerWebsiteService crawlerWebsiteService;

    @GetMapping("/list")
    public JsonResult list(){
        JsonResult jsonResult = new JsonResult();
        QueryWrapper<CrawlerWebsite> wrapper = new QueryWrapper<CrawlerWebsite>();
        wrapper.ne("is_del", "1");
        wrapper.ne("is_stop", "1");
        List<CrawlerWebsite> list = crawlerWebsiteService.list(wrapper);
        if(list != null && list.size() > 0) {
            return jsonResult.renderSuccess("查询成功！", "200", list);
        }

        return jsonResult.renderError("查询失败！");
    }
}
