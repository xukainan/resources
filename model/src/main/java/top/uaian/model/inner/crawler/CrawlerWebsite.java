package top.uaian.model.inner.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description:  爬虫网站<br>
 * date: 2020/5/6 17:45 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
@TableName("crawler_website")
public class CrawlerWebsite {
    /**
     * IdType.AUTO 	数据库ID自增
     * IdType.INPUT 	用户输入ID
     * IdType.ID_WORKER 	全局唯一ID，内容为空自动填充（默认配置）
     * IdType.UUID 	全局唯一ID，内容为空自动填充
     * @TableField(exist=false) 数据库中无该字段时，设置为false
     * 注：自定实体参数 （如 Entity entity） 前台传递格式   entity[0].属性名   entity[1].属性名
     */
    @TableId(type= IdType.AUTO)
    public int id;
    //mybatis不能用下划线 http://localhost:8090/admin/crawler/website/list
    public String webFlag;
    public String webName;
    public String webUrl;
    public Integer isDel;
    public Integer isStop;
    //@TableField(exist = false)
    public String crawlerNav;
}
