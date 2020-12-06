package com.gjz.test.multi.source.aop.web;

import com.gjz.test.multi.source.aop.entity.User;
import com.gjz.test.multi.source.aop.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * jdbcTemplateController
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/6 下午11:44
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/source/aop/jdbc")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 添加
     * @param user
     */
    @PostMapping(value = "create")
    void create(User user){
        userService.create(user);
    }

    /**
     * 删除
     * @param userId
     */
    @DeleteMapping(value = "delete")
    void delete(Long userId){
        userService.delete(userId);
    }

    /**
     *更新
     * @param user
     */
    @PostMapping(value = "update")
    void update(User user){
        userService.update(user);
    }

    /**
     * 查找
     * @param userId
     * @return
     */
    @GetMapping(value = "get")
    User get(Long userId) {
        return userService.get(userId);
    }

    /**
     * 查询
     * @return
     */
    List<User> list(){
        return new ArrayList<>();
    }
}

