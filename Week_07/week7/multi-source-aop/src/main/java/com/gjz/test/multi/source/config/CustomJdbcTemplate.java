package com.gjz.test.multi.source.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * <pre>
 * 自定义jdbctemplate
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7 上午12:50
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Component
public class CustomJdbcTemplate {

    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource masterSource){
        return new JdbcTemplate(masterSource);
    }


    @Bean(name = "salveJdbcTemplate")
    public JdbcTemplate salveJdbcTemplate(@Qualifier("salveDataSource") DataSource salveDataSource){
        return new JdbcTemplate(salveDataSource);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}

