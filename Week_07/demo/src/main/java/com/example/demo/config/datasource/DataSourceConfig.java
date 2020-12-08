package com.example.demo.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据源基础配置文件(多个数据源)
 */
@Configuration
public class DataSourceConfig {

    /**
     * 加载配置文件
     * @return
     */
    //@Primary
    @Bean(name = "ds1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 实例化数据源
     * @return
     */
    //@Primary
    @Bean(name = "ds1DataSource")
    public DataSource ds1DataSource() {
        DataSourceProperties dataSourceProperties = ds1DataSourceProperties();
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "ds1TransactionManager")
    public DataSourceTransactionManager ds1TransactionManager(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSourceProperties ds2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ds2DataSource")
    public DataSource ds2DataSource() {
        DataSourceProperties dataSourceProperties = ds2DataSourceProperties();
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "ds2TransactionManager")
    public DataSourceTransactionManager ds2TransactionManager(@Qualifier("ds2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}





