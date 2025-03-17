package com.yuhao.haorpc.fault.tolerant;

import com.yuhao.haorpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
/**
 * 容错策略：静默处理异常,记录日志，正常返回一个响应对象
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
