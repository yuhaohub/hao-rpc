package com.yuhao.haorpc.fault.retry;

import com.yuhao.haorpc.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {
    /**
     * 重试
     *
     * @param callable Callable 类代表一个任务
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
