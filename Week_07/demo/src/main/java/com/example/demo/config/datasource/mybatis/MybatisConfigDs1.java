package com.example.demo.config.datasource.mybatis;

import com.example.demo.config.datasource.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *mybatis多数据源1配置文件
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.ds1", sqlSessionTemplateRef = "ds1SqlSessionTemplate")
public class MybatisConfigDs1 {

    @Autowired
    private DataSourceConfig dataSourceConfig;
    /**
     * sessionFactory ds1
     * @return
     * @throws Exception
     */
    @Bean("ds1SqlSessionFactory")
    public SqlSessionFactory ds1SqlSessionFactory() throws Exception {
        DataSource dataSource = dataSourceConfig.ds1DataSource();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:com/example/demo/ds1/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "ds1SqlSessionTemplate")
    public SqlSessionTemplate ds1SqlSessionTemplate(@Qualifier("ds1SqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
