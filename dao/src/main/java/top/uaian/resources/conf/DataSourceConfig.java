package top.uaian.resources.conf;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import top.uaian.resources.model.DbItem;
import top.uaian.resources.model.KeyPair;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * description:  <br>
 * date: 2020/5/6 9:55 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Configuration
public class DataSourceConfig {

    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${dbconfig.file.location}")
    private String location;

    /**
     * 数据库配置文件的默认数据源名称
     */
    @Value("${dbconfig.defaultDataName}")
    private String defaultDataName;

    public Map<String, DbItem> loadDbItemMap(String location){
        if(StringUtils.isEmpty(location)) {
            throw new IllegalArgumentException(location + " : is null");
        }
        File dbFile = new File(location);//加载本地文件
        if(!dbFile.exists()) {
            try {
                dbFile = ResourceUtils.getFile(location);
                if(!dbFile.exists()) {
                    throw new FileNotFoundException(location + " : not exist");
                }
            }catch (FileNotFoundException fileNotFoundException){
                logger.info(location + " not exist:" + fileNotFoundException.getMessage());
            }

        }
        StringBuffer tmpConfigStr = new StringBuffer("");
        byte[] in  = new byte[512];
        int readCount = 0;
        try (FileInputStream fileInputStream = new FileInputStream(dbFile)){
            while ((readCount = fileInputStream.read(in, 0, in.length))  != -1) {
                tmpConfigStr.append(new String(in, 0, readCount));
            }
        }catch (IOException e){
            logger.info(location + " read error: " + e.getMessage());
        }
        return JSONObject.parseObject(tmpConfigStr.toString(),
                new TypeReference<Map<String, DbItem>>(){}.getType());
    }

    @Bean("dataSource")
    @Primary
    DynamicDataSource dataSource() throws Exception {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> targetDataSource = new HashMap<>();
        Map<String, DbItem> sqlDriverItemMaps = loadDbItemMap(location);
        sqlDriverItemMaps.keySet().forEach(sqlDriverItemMap_key -> {
            try {
                DataSource dataSource = buildDataSource(sqlDriverItemMaps.get(sqlDriverItemMap_key));
                targetDataSource.put(sqlDriverItemMap_key, dataSource);
                if(sqlDriverItemMap_key == defaultDataName) {
                    dynamicDataSource.setDefaultTargetDataSource(dataSource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dynamicDataSource.setTargetDataSources(targetDataSource);
        return dynamicDataSource;
    }

    private DataSource buildDataSource(DbItem sqlDriverItem) throws Exception {
        //druid 的配置，详见https://www.bookstack.cn/read/Druid/8bf7e2f602686bb8.md
        if(!Optional.ofNullable(sqlDriverItem).isPresent()) {
            try {
                throw new Exception("buildDataSource 参数sqlDriverItem 为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        KeyPair driverAndUrl = sqlDriverItem.getDriverAndUrl();
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(driverAndUrl.getValue());
        datasource.setUsername(sqlDriverItem.getDb_username());
        datasource.setPassword(sqlDriverItem.getDb_pw());
        datasource.setDriverClassName(driverAndUrl.getKey());

        datasource.setInitialSize(5);
        datasource.setMinIdle(1);
        datasource.setMaxActive(20);
        datasource.setMaxWait(10*1000L);
        datasource.setTimeBetweenEvictionRunsMillis(60 * 1000L);
        datasource.setMinEvictableIdleTimeMillis(300 * 1000L);
        //用来检测连接是否有效的sql
        datasource.setValidationQuery("select 1");
        //不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        datasource.setTestWhileIdle(true);
        //取消testOnBorrow，该值标识每次getConnection时都进行检测，比较影响性能
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setPoolPreparedStatements(false);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(20);
        try {
            //通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            datasource.setFilters("stat");
            List<Filter> filters = new ArrayList<>();
            filters.add(wallFilter());
            datasource.setProxyFilters(filters);
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }
        datasource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        return datasource;

    }

    @Bean
    WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    WallConfig wallConfig() {
        //配置详见https://www.bookstack.cn/read/Druid/ffdd9118e6208531.md
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);//允许执行DDL
        wallConfig.setStrictSyntaxCheck(false);//Druid SQL Parser在某些场景不能覆盖所有的SQL语法，出现解析SQL出错，可以临时把这个选项设置为false
        return wallConfig;
    }

}
