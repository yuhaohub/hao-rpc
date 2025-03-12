package com.yuhao.example.provider;

import com.yuhao.example.common.service.UserService;
import com.yuhao.haorpc.RpcApplication;
import com.yuhao.haorpc.config.RegistryConfig;
import com.yuhao.haorpc.config.RpcConfig;
import com.yuhao.haorpc.model.ServiceMetaInfo;
import com.yuhao.haorpc.registry.LocalRegistry;
import com.yuhao.haorpc.registry.Registry;
import com.yuhao.haorpc.registry.RegistryFactory;
import com.yuhao.haorpc.server.tcp.VertxTcpServer;

public class ProviderExp {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();
        String serviceName = UserService.class.getName();
        // 注册服务
        LocalRegistry.register(serviceName, UserServiceImpl.class);
        //注册到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 启动 web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        //使用Tcp服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
