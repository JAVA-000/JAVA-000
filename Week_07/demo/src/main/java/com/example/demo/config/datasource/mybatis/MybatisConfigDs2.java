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

import javax.sql.DataSource;

/**
 *mybatis多数据源2配置文件
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.ds2", sqlSessionTemplateRef = "ds2SqlSessionTemplate")
public class MybatisConfigDs2 {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * sessionFactory ds2
     * @return
     * @throws Exception
     */
    @Bean("ds2SqlSessionFactory")
    public SqlSessionFactory ds2SqlSessionFactory() throws Exception {
        DataSource dataSource = dataSourceConfig.ds2DataSource();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:com/example/demo/ds2/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "ds2SqlSessionTemplate")
    public SqlSessionTemplate ds2SqlSessionTemplate(@Qualifier("ds2SqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
