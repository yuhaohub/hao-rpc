package com.yuhao.example.consumer;

import com.yuhao.example.common.model.User;
import com.yuhao.example.common.service.UserService;
import com.yuhao.haorpc.config.RpcConfig;
import com.yuhao.haorpc.proxy.ServiceProxyFactory;
import com.yuhao.haorpc.utils.ConfigUtils;

public class ConsumerExp {
    //测试配置读取
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
       System.out.println(rpc);
        /**
         * 测试Mock
         */
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yuhao");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
