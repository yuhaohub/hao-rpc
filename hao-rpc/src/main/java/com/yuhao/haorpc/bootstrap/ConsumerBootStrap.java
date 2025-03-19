package com.yuhao.haorpc.bootstrap;

import com.yuhao.haorpc.RpcApplication;

public class ConsumerBootStrap {
    //初始化
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
