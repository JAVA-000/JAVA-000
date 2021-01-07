package com.gjz.test.redispubsub.receivers;

/**
 * <pre>
 * 接收消息接口
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 14:44
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@FunctionalInterface
public interface ReceiverInterface<T> {


    /**
     * 接收消息
     *
     * @param message
     */

    void receiveMessage(T message);
}

