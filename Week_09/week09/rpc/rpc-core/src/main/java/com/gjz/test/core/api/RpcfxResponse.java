package com.gjz.test.core.api;

import lombok.Data;

@Data
public class RpcfxResponse {
    private Object result;
    private Boolean status;
    private Exception exception;
}
