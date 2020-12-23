package com.gjz.test.rpc.api.entitys;

import lombok.Data;

@Data
public class RpcfxResponse {
    private Object result;
    private Boolean status;
    private Exception exception;
}
