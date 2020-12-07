package com.gjz.test.multi.source.service;

import com.gjz.test.multi.source.entity.User;

import java.util.List;

/**
 * <pre>
 * jdbcTemplateService
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/6 下午11:43
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface UserService {

    /**
     * 添加
     * @param user
     */
    void create(User user);

    /**
     * 删除
     * @param userId
     */
    void delete(Long userId);

    /**
     *更新
     * @param user
     */
    void update(User user);

    /**
     * 查找
     * @param userId
     * @return
     */
    User get(Long userId);

    /**
     * 查询
     * @return
     */
    List<User> list();
}
