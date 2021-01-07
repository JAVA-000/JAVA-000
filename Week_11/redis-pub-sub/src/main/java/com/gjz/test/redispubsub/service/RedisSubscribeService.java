package com.gjz.test.redispubsub.service;

import com.gjz.test.redispubsub.receivers.ReceiverInterface;

/**
 * <pre>
 * Redis订阅服务
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 14:53
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

public interface RedisSubscribeService {
/**
     * 订阅
     * @param topic
     * @param receiver
     */

    void subscribe(final String topic, ReceiverInterface receiver);
}
