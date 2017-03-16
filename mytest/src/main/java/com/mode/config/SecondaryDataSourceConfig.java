package com.mode.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhaoweiwei on 16/9/22.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.mode.dao2", sqlSessionFactoryRef="targetSqlSessionFactory")
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource2")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "targetSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondaryDataSource());
        return sessionFactory.getObject();
    }

    @Bean(name = "targetTm")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(secondaryDataSource());
    }

//
//    @Bean(name = "jdbcSecondaryTemplate")
//    public JdbcTemplate jdbcSecondaryTemplate(@Qualifier(value = "secondaryDataSource") DataSource secondaryDataSource) {
//        return new JdbcTemplate(secondaryDataSource);
//    }
}
