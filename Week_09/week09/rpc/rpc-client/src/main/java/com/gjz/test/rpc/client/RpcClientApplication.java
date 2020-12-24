package com.gjz.test.rpc.client;

import com.gjz.test.core.client.Rpcfx;
import com.gjz.test.rpc.api.entitys.User;
import com.gjz.test.rpc.api.service.UserService;

/**
 * <pre>
 * RPC客户端
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/23 下午9:13
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class RpcClientApplication {
    public static void main(String[] args) {
        UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/request");

        User user = userService.getUser(1L);

        System.out.println(user.getName());
    }
}

