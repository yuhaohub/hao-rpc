package com.yuhao.example.consumer;

import com.yuhao.haorpc.config.RpcConfig;
import com.yuhao.haorpc.utils.ConfigUtils;

public class ConsumerExp {
    //测试配置读取
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);

    }
}
