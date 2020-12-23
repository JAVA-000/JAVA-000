package com.gjz.test.rpc.api.service;

import com.gjz.test.rpc.api.entitys.User;

/**
 * <pre>
 * 用户服务类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/22 下午10:59
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    void createUser(User user);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    User getUser(Long id);

}
