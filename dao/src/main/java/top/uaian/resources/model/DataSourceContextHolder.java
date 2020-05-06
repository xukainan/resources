package top.uaian.resources.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * description:  <br>
 * date: 2020/5/6 13:29 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class DataSourceContextHolder {

    private static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);


    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 默认数据源
     */
    @Value("${dbconfig.defaultDataName}")
    private static String defaultDbSource = "";

    static String dbType;

    public static String getDbType() {
        if (contextHolder.get() == null) return defaultDbSource;
        else return contextHolder.get();
    }

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
        logger.debug("clean DbContextHolder database key");
    }
}
