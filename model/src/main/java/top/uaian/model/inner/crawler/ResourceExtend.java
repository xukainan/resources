package top.uaian.model.inner.crawler;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description:  <br>
 * date: 2020/5/12 13:53 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
@TableName("ResourceExtend")
public class ResourceExtend {
    private int id;
    private String resCode;
    private Integer resOrigin;
    private String resOriginUrl;
}
