package com.yuhao.haorpc.server.tcp;

import com.yuhao.haorpc.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

public class VertxTcpServer implements HttpServer {


//    private byte[] handleRequest(byte[] requestData) {
//        // 编写处理请求的逻辑，根据 requestData 构造响应数据并返回
//        // 实际逻辑需要根据具体的业务需求来实现
//        return "Hello, client!".getBytes();
//    }
    @Override
    public void doStart(int port) {
        //创建vertx实例
        Vertx vertx = Vertx.vertx();

        //创建Tcp服务器
        NetServer server = vertx.createNetServer();

        //处理请求
        server.connectHandler(new TcpServerHandle());
        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.err.println("Failed to start TCP server: " + result.cause());
            }
        });
    }
}
