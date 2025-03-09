package com.yuhao.haorpc.config;


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
}
