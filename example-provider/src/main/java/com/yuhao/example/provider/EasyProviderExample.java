package com.yuhao.example.provider;

import com.yuhao.example.common.service.UserService;
import com.yuhao.haorpc.registry.LocalRegistry;
import com.yuhao.haorpc.server.HttpServer;
import com.yuhao.haorpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
