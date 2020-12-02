package com.gjz.test.batch.insert;

import com.gjz.test.batch.insert.service.MybatisBatchInsertService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <pre>
 * Mybatis测试类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/2
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@SpringBootTest
public class MybatisBatchInsertServiceTest {


    @Resource
    MybatisBatchInsertService mybatisBatchInsertService;

    @Test
    public void testBatchInsertOrderData(){
        mybatisBatchInsertService.batchInsertOrderData();
    }
}

