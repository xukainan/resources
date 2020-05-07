package top.uaian.resources.service.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uaian.model.inner.crawler.CrawlerWebsite;
import top.uaian.resources.dao.admin.CrawlerWebsiteMapper;

import java.util.List;

/**
 * description:  <br>
 * date: 2020/5/7 16:16 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Service
public class CrawlerWebsiteServiceImpl extends ServiceImpl<CrawlerWebsiteMapper, CrawlerWebsite> implements CrawlerWebsiteService{

    @Override
    public List<CrawlerWebsite> list(Wrapper<CrawlerWebsite> queryWrapper) {
        return super.list(queryWrapper);
    }

}
