package com.yuhao.example.consumer;

import com.yuhao.example.common.model.User;
import com.yuhao.example.common.service.UserService;
import com.yuhao.haorpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // 代理
        UserService userService =ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yuhao");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
