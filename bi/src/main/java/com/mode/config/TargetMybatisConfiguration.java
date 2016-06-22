package com.mode.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by zhaoweiwei on 16/6/20.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.mode.dao.target", sqlSessionFactoryRef="targetSqlSessionFactory")
public class TargetMybatisConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver properties;

    @Override
    public void setEnvironment(Environment env) {
        this.properties = new RelaxedPropertyResolver(env, "datasource.target.");
    }

    @Bean(name = "targetDataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    public DataSource targetDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setMaxActive(properties.getProperty("maxActive", Integer.class, 1));
        dataSource.setInitialSize(properties.getProperty("initialSize", Integer.class, 1));
        return dataSource;
    }


    @Bean(name = "targetSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(targetDataSource());
        return sessionFactory.getObject();
    }
}
