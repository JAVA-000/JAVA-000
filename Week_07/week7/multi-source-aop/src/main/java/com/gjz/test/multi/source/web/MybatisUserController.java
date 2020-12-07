package com.gjz.test.multi.source.web;

import com.gjz.test.multi.source.entity.User;
import com.gjz.test.multi.source.service.MybatisUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * Mybatis Service Controller
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/7 下午10:50
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/source/aop/mybatis/user")
public class MybatisUserController{

    @Resource
    MybatisUserService mybatisUserService;

    /**
     * 添加
     *
     * @param user
     */
    @PostMapping(value = "create")
    public void create(User user) {
        mybatisUserService.create(user);
    }


    /**
     * 查找
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "get")
    public User get(Long userId) {
        return mybatisUserService.get(userId);
    }

    /**
     * 查询
     *
     * @return
     */
    @GetMapping(value = "list")
    public List<User> list() {
        return mybatisUserService.list();
    }
}

