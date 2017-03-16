package com.mode.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 获取数据库的连接信息，在application.properties中配置，并指定特定的前缀
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.mode.dao", sqlSessionFactoryRef="sourceSqlSessionFactory")
public class MybatisConfiguration implements EnvironmentAware
{

    private RelaxedPropertyResolver properties;

    @Override
    public void setEnvironment(Environment env) {
        this.properties = new RelaxedPropertyResolver(env, "mode.jdbc.");
    }

//    @Value("${mode.jdbc.driverClassName}")
//    private String driver;
//
//    @Value("${mode.jdbc.url}")
//    private String url;
//
//    @Value("${mode.jdbc.username}")
//    private String username;
//
//    @Value("${mode.jdbc.password}")
//    private String password;
//
//    @Value("${mode.jdbc.maxActive}")
//    private String maxActive;
//
//    @Value("${mode.jdbc.initialSize}")
//    private String initialSize;

//    @Bean(name = "datasource", destroyMethod = "close", initMethod = "init")
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(properties.getProperty("driverClassName"));
//        dataSource.setUrl(properties.getProperty("url"));
//        dataSource.setUsername(properties.getProperty("username"));
//        dataSource.setPassword(properties.getProperty("password"));
//        dataSource.setMaxActive(properties.getProperty("maxActive", Integer.class, 1));
//        dataSource.setInitialSize(properties.getProperty("initialSize", Integer.class, 1));
//
////        System.out.println("driver -> " + driver);
////        dataSource.setDriverClassName(driver);
////        dataSource.setUrl(url);
////        dataSource.setUsername(username);
////        dataSource.setPassword(password);
////        dataSource.setMaxActive(Integer.parseInt(maxActive));
////        dataSource.setInitialSize(Integer.parseInt(initialSize));
//        return dataSource;
//    }

    @Bean(name = "datasource")
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(properties.getProperty("url"));
        builder.driverClassName(properties.getProperty("driverClassName"));
        builder.username(properties.getProperty("username"));
        builder.password(properties.getProperty("password"));
        return builder.build();
    }

//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }

    @Bean(name = "sourceSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }
}