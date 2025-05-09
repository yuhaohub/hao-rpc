package com.yuhao.haorpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.yuhao.haorpc.RpcApplication;
import com.yuhao.haorpc.config.RpcConfig;
import com.yuhao.haorpc.fault.retry.RetryStrategy;
import com.yuhao.haorpc.fault.retry.RetryStrategyFactory;
import com.yuhao.haorpc.fault.tolerant.TolerantStrategy;
import com.yuhao.haorpc.fault.tolerant.TolerantStrategyFactory;
import com.yuhao.haorpc.loadbalancer.LoadBalancer;
import com.yuhao.haorpc.loadbalancer.LoadBalancerFactory;
import com.yuhao.haorpc.model.RpcRequest;
import com.yuhao.haorpc.model.RpcResponse;
import com.yuhao.haorpc.model.ServiceMetaInfo;
import com.yuhao.haorpc.registry.Registry;
import com.yuhao.haorpc.registry.RegistryFactory;
import com.yuhao.haorpc.serializer.Serializer;
import com.yuhao.haorpc.serializer.SerializerFactory;
import com.yuhao.haorpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        /**
//         * 使用系统内置的序列化器
//         */
//        Serializer serializer = null;
//        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
//        for (Serializer ser : serviceLoader) {
//            serializer = ser;
//            break;
//        }
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        String serviceName = method.getDeclaringClass().getName();
        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            //serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // 使用负载均衡算法选择服务提供者（负载均衡）
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            // 发送 TCP 请求
            //使用重试机制
            RpcResponse rpcResponse ;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }

        return rpcResponse.getData();
    }
}
