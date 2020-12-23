package com.gjz.test.rpc.api.entitys;

import lombok.Data;

/**
 * <pre>
 * 用户
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/22 下午10:57
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String address;


}

