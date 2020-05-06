package top.uaian.resources.conf;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@MapperScan("top.uaian.mybatisplus.dao")
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public MybatisConfiguration globalConfiguration(){
        return new MybatisConfiguration();
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(MybatisConfiguration mybatisConfiguration ) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        return mybatisSqlSessionFactoryBean.getObject();
    }

}
