package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/*@Configuration
@MapperScan(basePackages = "com.example.demo.**")
@ImportAutoConfiguration(MybatisAutoConfiguration.class)*/
public class DataSourceConfig {

/*    *//**
     * 加载配置文件
     *
     * @return
     *//*
    @Bean(name = "ds1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource ds1DataSource() {
        return DataSourceBuilder.create().build();
    }

    *//**
     * 事务管理器
     *
     * @param dataSource
     * @return
     *//*
    @Bean(name = "ds1TransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/


}
