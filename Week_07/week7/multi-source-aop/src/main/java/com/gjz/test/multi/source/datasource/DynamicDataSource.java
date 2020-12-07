package com.gjz.test.multi.source.datasource;

import com.gjz.test.multi.source.enums.DataSourceEnum;
import com.gjz.test.multi.source.holder.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 动态切换数据源
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Slf4j
@Component("dataSource")
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Resource
    DataSource masterDataSource;

    @Resource
    DataSource salveDataSource;

    @PostConstruct
    public void initializingBean(){
        // 注册主从数据源
        Map<Object, Object> targetDataSources = new HashMap<>(2,1);
        targetDataSources.put(DataSourceEnum.MASTER.getValue(), masterDataSource);
        targetDataSources.put(DataSourceEnum.SALVE.getValue(), salveDataSource);
        setTargetDataSources(targetDataSources);

        // 默认主库
        setDefaultTargetDataSource(masterDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("{}, determineCurrentLookupKey, 选择数据源======》" ,"【数据源路由器】");
        return DynamicDataSourceHolder.getDataSourceKey();
    }




}

