package com.yuhao.haorpc.fault.tolerant;

import com.yuhao.haorpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 */
public interface TolerantStrategy {
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
