package com.gjz.test.batch.insert.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 批量插入数据
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
public interface BatchInsertService {

    /**
     * 批量插入数据，批量记录
     */
    void batchInsertBatchRecord();


    /**
     * 批量插入数据，内容拼接在sql后面
     */
    void batchInsertOrderDataMoreValues();

}
