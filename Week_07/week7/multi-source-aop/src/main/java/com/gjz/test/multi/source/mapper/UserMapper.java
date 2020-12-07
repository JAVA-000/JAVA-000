package com.gjz.test.multi.source.mapper;

import com.gjz.test.multi.source.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author guojz
 */
@Mapper
public interface UserMapper {

    /**
     * 获取员工
     * @param userId
     * @return
     */
    User getUser(Long userId);

    /**
     * 查询所有员工
     * @return
     */
    List<User> listUser();

    /**
     * 添加员工
     * @param user
     */
    void createUser(User user);
}