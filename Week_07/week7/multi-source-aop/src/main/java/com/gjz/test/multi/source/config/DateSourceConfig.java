package com.gjz.test.multi.source.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <pre>
 * 多数据源配置
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7 上午12:42
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Configuration
public class DateSourceConfig {

    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "salveDataSource")
    @Qualifier("salveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.salve")
    public DataSource salveDataSource(){
        return DataSourceBuilder.create().build();
    }
}

