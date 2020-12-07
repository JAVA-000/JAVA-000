package com.gjz.test.multi.source.holder;

import com.gjz.test.multi.source.enums.DataSourceEnum;

/**
 * <pre>
 * 记录当前线程使用的数据源
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7 14:07
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DynamicDataSourceHolder {
    /**
     * 保存当前数据
     */
    private static ThreadLocal<String> holder = new ThreadLocal<>();


    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }

    /**
     * 线程数据源标记为主库
     */
    public static void markMaster(){
        holder.set(DataSourceEnum.MASTER.getValue());
    }

    /**
     * 线程数据源标记为从库
     */
    public static void markSalve(){
        holder.set(DataSourceEnum.SALVE.getValue());
    }
}

