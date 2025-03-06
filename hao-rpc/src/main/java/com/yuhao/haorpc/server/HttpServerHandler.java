package com.yuhao.haorpc.server;

import com.yuhao.haorpc.RpcApplication;
import com.yuhao.haorpc.model.RpcRequest;
import com.yuhao.haorpc.model.RpcResponse;
import com.yuhao.haorpc.registry.LocalRegistry;
import com.yuhao.haorpc.serializer.Serializer;
import com.yuhao.haorpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * http请求处理
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
//    反序列化请求为对象，并从请求对象中获取参数。
//    根据服务名称从本地注册器中获取到对应的服务实现类。
//    通过反射机制调用方法，得到返回结果。
//    对返回结果进行封装和序列化，并写入到响应中。
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        //final Serializer serializer = new JdkSerializer();
        // 序列化器(可通过全局配置选择 JSON、Kryo、Hessian)
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 记录日志
        System.out.println("Received request: " + httpServerRequest.method() + " " + httpServerRequest.uri());
        httpServerRequest.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            }catch (Exception e){
                e.printStackTrace();
            }

            //构造响应对象
            RpcResponse rpcResponse = new RpcResponse();
            if(rpcRequest == null){
                rpcResponse.setMessage("请求为空");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }
            try {
                // 获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(httpServerRequest, rpcResponse, serializer);
        });



    }

    /**
     *  响应
     * @param httpServerRequest
     * @param rpcResponse
     * @param serializer
     */
    /**
     * 响应
     *
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
