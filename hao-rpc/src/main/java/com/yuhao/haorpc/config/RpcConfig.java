package com.yuhao.haorpc.config;


import com.yuhao.haorpc.fault.retry.RetryStrategyKeys;
import com.yuhao.haorpc.fault.tolerant.TolerantStrategyKeys;
import com.yuhao.haorpc.loadbalancer.LoadBalancerKeys;
import com.yuhao.haorpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 配置对象带默认值
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "hao-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    /**
     * 服务器端口号
     */
    private int serverPort = 8080;
    /**
     * 模拟调用 mock
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;
    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;
}
