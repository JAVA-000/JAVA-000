package com.gjz.test.service;

import com.gjz.test.rpc.api.entitys.User;
import com.gjz.test.rpc.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 用户服务
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/23 下午10:46
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service("com.gjz.test.rpc.api.service.UserService")
public class UserServiceImpl implements UserService {
    private static final String LOGGER_HEAD = "【 用户服务 】";
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 创建用户
     *
     * @param user
     */
    @Override
    public void createUser(User user) {
        log.info("用户服务类，创建用户， user:{}", user);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setAddress("address" + id);
        user.setId(id);
        user.setAge(id.intValue() * 10);
        user.setName("user" + id);
        return user;
    }
}

