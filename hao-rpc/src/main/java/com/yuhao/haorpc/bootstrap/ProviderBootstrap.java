package com.yuhao.haorpc.bootstrap;

import com.yuhao.haorpc.RpcApplication;
import com.yuhao.haorpc.config.RegistryConfig;
import com.yuhao.haorpc.config.RpcConfig;
import com.yuhao.haorpc.model.ServiceMetaInfo;
import com.yuhao.haorpc.model.ServiceRegisterInfo;
import com.yuhao.haorpc.registry.LocalRegistry;
import com.yuhao.haorpc.registry.Registry;
import com.yuhao.haorpc.registry.RegistryFactory;
import com.yuhao.haorpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {

    /**
     * 初始化
     * @param serviceRegisterInfoList
     */
    public static void init(List<ServiceRegisterInfo> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
        // 启动 web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}