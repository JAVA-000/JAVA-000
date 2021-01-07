package com.gjz.test.redispubsub.service;

/**
 * <pre>
 * 发布消息服务
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 14:28
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

public interface RedisPublishService<T> {

    /**
     * 发布消息
     * @param message
     */
    void publish(final String topic, final T message);
}

