package top.uaian.model.inner.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description:  <br>
 * date: 2020/5/12 13:34 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
@TableName("resource")
public class Resource {
    @TableId(type= IdType.AUTO)
    private int id;
    private String code;
    private String name;
    private int type;
    private int isDel;
    private int isIvalid;
    private int size;
    private String[] screenshots;
    private String resUrl;
    private String resPwd;
    private ResourceExtend resourceExtend;
}
