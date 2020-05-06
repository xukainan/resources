package top.uaian.resources.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * description:  数据库配置<br>
 * date: 2020/5/6 10:55 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Data
public class DbItem {

    @JSONField(name = "DBname")
    private String db_name;

    @JSONField(name = "DBtype")
    private String db_type;

    @JSONField(name = "DBaddr")
    private String db_addr;

    @JSONField(name = "DBport")
    private String db_port;

    @JSONField(name = "DBusertype")
    private String db_usertype;

    @JSONField(name = "DBusername")
    private String db_username;

    @JSONField(name = "DBpw")
    private String db_pw;

    /**
     * 获取驱动名称和jdbc url
     *
     * @return pair：第一个为driver名称  第二个为jdbc url
     */
    public KeyPair getDriverAndUrl() throws Exception {
        String lowerdb_type = db_type.toLowerCase();

        switch (lowerdb_type) {
            case "mysql":
                return new KeyPair("com.mysql.jdbc.Driver",
                        "jdbc:mysql://" + db_addr + ":" + db_port + "/" + db_name + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
            case "oracle":
                return new KeyPair("oracle.jdbc.driver.OracleDriver",
                        "jdbc:oracle:thin:@" + db_addr + ":" + db_port + ":" + db_name);
            case "sqlserver":
                return new KeyPair("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                        "jdbc:sqlserver://" + db_addr + ":" + db_port + ";DatabaseName=" + db_name);
            case "vertica":// jdbc:vertica://124.251.48.127:5433/vertica_fwyun
                return new KeyPair("com.vertica.jdbc.Driver",
                        "jdbc:vertica://" + db_addr + ":" + db_port + "/" + db_name);
            default:
                throw new Exception("not support jdbc driver");
        }
    }
}
