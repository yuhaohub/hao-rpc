package com.yuhao.example.common.service;

import com.yuhao.example.common.model.User;

public interface UserService {

    User getUser(User user);

    /**
     * 新方法 - 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
