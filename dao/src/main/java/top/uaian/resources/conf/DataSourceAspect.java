package top.uaian.resources.conf;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.uaian.resources.model.DataSourceContextHolder;
import top.uaian.resources.model.DbSourceConstant;

/**
 * description:  <br>
 * date: 2020/3/31 16:49 <br>
 * @author: xukainan <br>
 * version: 1.0 <br>
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* top.uaian.resources.dao.*Mapper.sel*(..))" +
                "|| execution(* top.uaian.resources.dao.*Mapper.count*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.find*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.get*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.list*(..))")
    private void db_read(){}

    @Before("db_read()")
    public void db_setReadDataSource(){
        if (logger.isDebugEnabled()) {
            logger.info("切换到db读库");
        }
        DataSourceContextHolder.setDbType(DbSourceConstant.db_READ);
    }

    @After("db_read()")
    public void db_releaseReadDataSource(){
        if (logger.isDebugEnabled()) {
            logger.info("释放db读库");
        }
        DataSourceContextHolder.clearDbType();
    }

    @Pointcut("execution(* top.uaian.resources.dao.*Mapper.insert*(..))"+
            "|| execution(* top.uaian.resources.dao.*Mapper.update*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.wget*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.save*(..))" +
            "|| execution(* top.uaian.resources.dao.*Mapper.del*(..))"
    )
    private void db_write(){};

    @Before("db_write()")
    public void db_setWriteDataSource(){
        if (logger.isDebugEnabled()) {
            logger.info("切换到db写库");
        }
        DataSourceContextHolder.setDbType(DbSourceConstant.db_WRITE);
    }

    @After("db_write()")
    public void db_releaseWriteDataSource(){
        if (logger.isDebugEnabled()) {
            logger.info("释放db写库");
        }
        DataSourceContextHolder.clearDbType();
    }
}
