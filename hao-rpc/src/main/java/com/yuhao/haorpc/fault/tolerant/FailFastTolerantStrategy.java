package com.yuhao.haorpc.fault.tolerant;

import com.yuhao.haorpc.model.RpcResponse;

import java.util.Map;
/**
 * 快速失败容错策略
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
