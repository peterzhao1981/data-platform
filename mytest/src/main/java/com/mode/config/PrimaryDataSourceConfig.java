package com.mode.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhaoweiwei on 16/9/22.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.mode.dao", sqlSessionFactoryRef="sourceSqlSessionFactory")
public class PrimaryDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sourceSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource());
        return sessionFactory.getObject();
    }

    @Bean(name = "sourceTm")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(primaryDataSource());
    }

//    @Bean(name = "jdbcPrimaryTemplate")
//    public JdbcTemplate jdbcPrimaryTemplate(@Qualifier(value = "primaryDataSource") DataSource primaryDataSource) {
//        return new JdbcTemplate(primaryDataSource);
//    }

}
