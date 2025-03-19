package com.yuhao.example.provider;

import com.yuhao.example.common.service.UserService;
import com.yuhao.haorpc.bootstrap.ProviderBootstrap;
import com.yuhao.haorpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

public class ProviderExp {

    public static void main(String[] args) {

        List<ServiceRegisterInfo>   serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        ProviderBootstrap.init(serviceRegisterInfoList);


    }
}
