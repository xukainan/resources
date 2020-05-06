package top.uaian.resources.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import top.uaian.resources.model.DataSourceContextHolder;

/**
 * description:  <br>
 * date: 2020/5/6 13:23 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
    }
}
